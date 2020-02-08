package cn.bload.forum.config;

import com.google.common.collect.Ordering;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import cn.bload.forum.base.Page;
import cn.bload.forum.constenum.Result;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/1/13 14:22
 * @describe 类描述:
 */
@Configuration
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        Ordering ordering = new Ordering() {
            @Override
            public int compare(Object object, Object t1) {
                return 0;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.bload.forum"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Page.class)
                .globalResponseMessage(RequestMethod.GET, getResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getResponseMessages());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("论坛 API 文档")
                .description("论坛 API 网关接口")
                .version("1.0.0")
                .build();
    }


    /**
     * 设置响应报文
     *
     * @return 响应报文
     */
    private List<ResponseMessage> getResponseMessages() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder()
                .code(Result.SUCCESS.getCode())
                .message(Result.SUCCESS.getMessage())
                .responseModel(new ModelRef("ResultBean")).build());
        responseMessageList.add(new ResponseMessageBuilder()
                .code(Result.ERROR.getCode())
                .message(Result.ERROR.getMessage())
                .responseModel(new ModelRef("ResultBean")).build());
        responseMessageList.add(new ResponseMessageBuilder()
                .code(Result.FAIL.getCode())
                .message(Result.FAIL.getMessage())
                .responseModel(new ModelRef("ResultBean")).build());
        return responseMessageList;
    }
}
