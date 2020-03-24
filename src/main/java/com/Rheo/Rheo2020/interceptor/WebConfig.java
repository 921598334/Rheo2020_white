package com.Rheo.Rheo2020.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/upload/";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        //当在浏览器上访问localhost：//upload/xxx时会自动去访问后面配置的路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+fullPath,"classpath:/static/upload/"); //添加这一行

    }


    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        String[] excludes = new String[]{"/","/bind/**","/upload/**","/login/**","/sign/**","/error/**","/question/**","/login/**","/static/**","/index/**","/css/**","/fonts/**","/images/**","/js/**","/Wopop_files/**","/callback/**","/ckeditor/**","/logout/**"};
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
//
//    }

}
