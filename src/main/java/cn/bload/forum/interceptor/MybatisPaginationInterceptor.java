package cn.bload.forum.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bload.forum.base.BaseQuery;
import lombok.extern.log4j.Log4j2;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/20 10:19
 * @describe 类描述:
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
@Log4j2
public class MybatisPaginationInterceptor extends PaginationInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType()) {
            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
            Object paramObj = boundSql.getParameterObject();
            IPage<?> page = null;

            if (paramObj instanceof IPage) {
                page = (IPage)paramObj;
            } else if (paramObj instanceof Map) {
                Iterator var8 = ((Map)paramObj).values().iterator();

                while(var8.hasNext()) {
                    Object arg = var8.next();
                    if (arg instanceof IPage) {
                        page = (IPage)arg;
                        break;
                    }
                }
            }else if (paramObj instanceof BaseQuery){   //实现自动注入basequery中的page对象
                page = ((BaseQuery)paramObj).getPage();
            }

            if (null != page && page.getSize() >= 0L) {
                if (this.limit > 0L && this.limit <= page.getSize()) {
                    this.handlerLimit(page);
                }

                String originalSql = boundSql.getSql();
                Connection connection = (Connection)invocation.getArgs()[0];
                DbType dbType = StringUtils.isNotBlank(this.dialectType) ? DbType.getDbType(this.dialectType) : JdbcUtils.getDbType(connection.getMetaData().getURL());
                if (page.isSearchCount()) {
                    SqlInfo sqlInfo = SqlParserUtils.getOptimizeCountSql(page.optimizeCountSql(), this.countSqlParser, originalSql);
                    this.queryTotal(sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
                    if (page.getTotal() <= 0L) {
                        return null;
                    }
                }

                String buildSql = concatOrderBy(originalSql, page);
                log.debug(buildSql);
                DialectModel model = DialectFactory.buildPaginationSql(page, buildSql, dbType, this.dialectClazz);
                Configuration configuration = mappedStatement.getConfiguration();
                List<ParameterMapping> mappings = new ArrayList(boundSql.getParameterMappings());
                Map<String, Object> additionalParameters = (Map)metaObject.getValue("delegate.boundSql.additionalParameters");
                model.consumers(mappings, configuration, additionalParameters);
                metaObject.setValue("delegate.boundSql.sql", model.getDialectSql());
                metaObject.setValue("delegate.boundSql.parameterMappings", mappings);
                return invocation.proceed();
            } else {
                return invocation.proceed();
            }
        } else {
            return invocation.proceed();
        }
    }
}
