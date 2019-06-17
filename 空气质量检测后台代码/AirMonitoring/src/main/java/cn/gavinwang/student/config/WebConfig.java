package cn.gavinwang.student.config;

import cn.gavinwang.student.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截的路径
        String [] addPathPatterns = {
                "/getairinfo",
                "/getmyfamilies",
                "/createfamily",
                "/joinfamily",
                "/addroom",
        };

        //不拦截的路径
        String [] excludePathPatterns = {
                //"/postData","/register","/login"
        };

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);

    }
}