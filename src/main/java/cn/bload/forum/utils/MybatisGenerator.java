package cn.bload.forum.utils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/10 12:57
 * @describe 类描述:
 */
public class MybatisGenerator {
    //表前缀，支持多个
    private final static String[] TABLE_PREFIX = new String[]{"forum"};

    private final static String PROJECT_PATH = System.getProperty("user.dir");


    private final static String RESOURCES_PATH = PROJECT_PATH + "/src/main/resources/";

    private final static String PARENT_PACKAGE = "cn.bload.forum";

    //是否覆盖文件
    private final static Boolean IS_FILE_OVERRIDER = true;

    //库名
    private final static String DB_NAME = "bl_forum";
    //数据库配置
    private final static String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private final static String DB_USERNAME = "root";
    private final static String DB_PASSWORD = "root";
    private final static String DB_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";




    public static void main(String[] args) throws IOException {
        if (scanner("请选择生成方式：\n1. 按表名生成 2.全部生成").equals("2")){
            generatorAllTable();
        }else{
            generator(scanner("表名，多个英文逗号分割").split(","));
        }


    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 生成多个表数据
     * @param tableNames 表名，可以数组多个
     */
    public static void generator(String... tableNames){
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());

        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());

        // 策略配置
        mpg.setStrategy(getStrategyConfig(tableNames));


        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 模板配置
        mpg.setTemplate(getTemplateConfig());

        //生成自定义vue模板
        mpg.setCfg(generatorOtherTemplate());

        // 包配置
        mpg.setPackageInfo(getPackageConfig());

        // 执行生成
        mpg.execute();
    }

    private static GlobalConfig getGlobalConfig(){
        GlobalConfig gc = new GlobalConfig();
        gc.setSwagger2(true);
        gc.setOpen(false);
        //输出文件路径
        gc.setOutputDir(PROJECT_PATH + "/src/main/java");
        // 是否覆盖文件，*****重要！！！！
        gc.setFileOverride(IS_FILE_OVERRIDER);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        // 作者
        gc.setAuthor("blowsnow");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");

        return gc;
    }

    private static StrategyConfig getStrategyConfig(String... tableNames){
        StrategyConfig strategy = new StrategyConfig();
        // 表前缀
        strategy.setTablePrefix(TABLE_PREFIX);
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(tableNames);

        // 设置 继承类
        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        //一般IDE自动引用，如果不 请自行加包路径 parentPackage
        strategy.setSuperControllerClass(PARENT_PACKAGE+".controller.BaseController");

        // 是否使用lombok
        strategy.setEntityLombokModel(true);
        // 是否开启restcontroller
        strategy.setRestControllerStyle(true);
        return strategy;
    }

    private static TemplateConfig getTemplateConfig(){
        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setController(null);
//        tc.setController("/templates/controller.java");
        tc.setService("/templates/service.java");
        tc.setServiceImpl("/templates/serviceImpl.java");
        tc.setEntity("/templates/entity.java");
        tc.setMapper("/templates/mapper.java");
        tc.setXml("/templates/mapper.xml");
        return tc;
    }

    private static PackageConfig getPackageConfig(){
        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT_PACKAGE);
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setEntity("model");
        pc.setXml("mapper");
        return pc;
    }

    //生成自定义其他页面模板
    private static InjectionConfig generatorOtherTemplate(){
        Boolean bool = false;
        if (!bool){
            return null;
        }
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();

                ArrayList<Map<String,String>> list = new ArrayList<>();

                //接口列表
                Map<String,String> map2 = new HashMap<>();
                map2.put("method","Get");
                map2.put("name","list");
                list.add(map2);
                map2 = new HashMap<>();
                map2.put("method","Get");
                map2.put("name","listPage");
                list.add(map2);
                map2 = new HashMap<>();
                map2.put("method","Post");
                map2.put("name","add");
                list.add(map2);
                map2 = new HashMap<>();
                map2.put("method","Post");
                map2.put("name","update");
                list.add(map2);
                map2 = new HashMap<>();
                map2.put("method","Post");
                map2.put("name","del");
                list.add(map2);
                map2 = new HashMap<>();
                map2.put("method","Get");
                map2.put("name","get");
                list.add(map2);

                map.put("apis",list);
                map.put("abc","123");
                this.setMap(map);
            }
        };

        String path = RESOURCES_PATH + "vueOut/";
        // 自定义 store 生成
        List<FileOutConfig> focList = new ArrayList<>();

        String apiOutPath = path+"utils/api/";
        focList.add(new FileOutConfig("/templates/vue/api.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return apiOutPath + tableInfo.getEntityName().toLowerCase()+".js";
            }
        });

        String storeOutPath = path+"store/";
        focList.add(new FileOutConfig("/templates/vue/storeIndex.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return storeOutPath + tableInfo.getEntityName().toLowerCase() + "/index.js";
            }
        });
        focList.add(new FileOutConfig("/templates/vue/storeActions.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return storeOutPath + tableInfo.getEntityName().toLowerCase() + "/actions.js";
            }
        });
        focList.add(new FileOutConfig("/templates/vue/storeGetters.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return storeOutPath + tableInfo.getEntityName().toLowerCase() + "/getters.js";
            }
        });
        focList.add(new FileOutConfig("/templates/vue/storeMutations.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return storeOutPath + tableInfo.getEntityName().toLowerCase() + "/mutations.js";
            }
        });


        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public static DataSourceConfig getDataSourceConfig(){
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        dsc.setDriverName(DB_DRIVER_CLASS_NAME);
        String dbUrl = DB_URL;
        dsc.setDbType(JdbcUtils.getDbType(dbUrl));   //自动获取类型

        dsc.setUrl(dbUrl);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 转换时间不使用localdate
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                String t = fieldType.toLowerCase();
                if ("datetime".equals(t)) {
                    return DbColumnType.DATE;
                }
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(globalConfig,fieldType);
            }
        });
        return  dsc;
    }


    /**
     * 生成全部表数据
     * 注意：***如果开启了覆盖，会覆盖原来的数据！！！！
     */
    public static void generatorAllTable(){
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        Connection conn = dataSourceConfig.getConn();

        System.out.println(conn);
        String database = DB_NAME;
        List<String> allTableNames = getAllTableNames(conn, null, database);
        String[] objects = allTableNames.toArray(new String[allTableNames.size()]);
        generator(objects);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定数据库和用户的所有表名
     * @param conn 连接数据库对象
     * @param user 用户
     * @param database 数据库名
     * @return
     */
    public static List<String> getAllTableNames(Connection conn, String user, String database) {
        List<String> tableNames = new ArrayList();
        if (conn != null) {
            try {
                DatabaseMetaData dbmd = conn.getMetaData();
                // 表名列表
                ResultSet rest = dbmd.getTables(database, null, null, new String[] { "TABLE" });
                // 输出 table_name
                while (rest.next()) {
                    tableNames.add(rest.getString("TABLE_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tableNames;
    }
}
