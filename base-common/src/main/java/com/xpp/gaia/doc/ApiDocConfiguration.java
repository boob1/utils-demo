package com.xpp.gaia.doc;

import com.xpp.gaia.boot.hook.StartHook;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.CollectionUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Api接口配置
 *
 * @author Akira
 * @since 2021/11/24
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
@EnableConfigurationProperties(ApiDocProperties.class)
@DependsOn("com.xpp.gaia.boot.hook.StartHook")
public class ApiDocConfiguration {

    @Autowired
    ApiDocProperties apiDocProperties;

    @Autowired
    ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory autowireCapableBeanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        if (CollectionUtils.isEmpty(apiDocProperties.getGroups())) {
            registerDocketBeanByDefault(autowireCapableBeanFactory);
        } else {
            registerDocketBeanByProperties(autowireCapableBeanFactory);
        }
    }

    public void registerDocketBeanByDefault(DefaultListableBeanFactory autowireCapableBeanFactory) {
        log.info("Initializing Gaia ApiDocConfiguration[default]");
        autowireCapableBeanFactory.registerSingleton(
                "defaultDocket",
                docket(StartHook.appPackage, RequestHandlerSelectors.withClassAnnotation(Api.class))
        );
    }

    public void registerDocketBeanByProperties(DefaultListableBeanFactory autowireCapableBeanFactory) {
        log.info("Initializing Gaia ApiDocConfiguration[group]");
        List<ApiDocProperties.Group> groups = apiDocProperties.getGroups();
        for (ApiDocProperties.Group group : groups) {
            String beanName = String.format("%sDocket", group.getName());
            autowireCapableBeanFactory.registerSingleton(
                    beanName,
                    docket(group.getName(), RequestHandlerSelectors.basePackage(group.getBasePackage())));
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(nullOrDefault(apiDocProperties.getTitle(),
                        String.format("%s 接口文档", StartHook.appName)))
                .description(nullOrDefault(apiDocProperties.getDescription(),
                        String.format("# %s RESTful APIs", StartHook.appName)))
                .termsOfServiceUrl(nullOrDefault(apiDocProperties.getServiceUrl(),
                        "http://10.99.3.65/gitlab"))
                .version(StartHook.appVersion)
                .contact(new Contact(nullOrDefault(apiDocProperties.getAuthor(), "xpp"), "", ""))
                .build();
    }

    private Docket docket(String groupName, Predicate<RequestHandler> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build();
    }

    private String nullOrDefault(String mayNullVal, String defaultVal) {
        return mayNullVal == null ? defaultVal : mayNullVal;
    }
}
