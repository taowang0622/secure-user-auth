package io.github.taowang0622.web.config;

import com.google.common.base.Predicates;
import io.github.taowang0622.web.filter.TimeFilter;
import io.github.taowang0622.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
//    @Autowired
//    TimeInterceptor timeInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor);
//    }


//    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        TimeFilter timeFilter = new TimeFilter();
        filterRegistrationBean.setFilter(timeFilter);

        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);

        return filterRegistrationBean;
    }

    @Bean
    public Docket swaggerSpringMvcConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //Spring-boot has some pre-defined controllers/enpoints/RESTful APIs!!
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My REST API",
                "APIs from the module security-demo and security-browser",
                "0.0.1",
                "Terms of service",
                new Contact("Tao Wang", "https://taowang0622.github.io/", "taowang0622@gmail.com"),
                "License of API",
                "API license URL", Collections.emptyList());
    }
}
