package com.douzone.mysite.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.mysite.security.AuthInterceptor;
import com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.mysite.security.LoginInterceptor;
import com.douzone.mysite.security.LogoutInterceptor;
import com.douzone.mysite.security.SiteUpdateInterceptor;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {
	// Argument Resolver
	@Bean
	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(handlerMethodArgumentResolver());
	}

//	// Site Inteceptor
//	@Bean
//	public HandlerInterceptor siteInterceptor() {
//		return new SiteInterceptor();
//	}

	// Security Interceptors
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}

	@Bean
	public HandlerInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Bean
	public HandlerInterceptor siteUpdateInterceptor() {
		return new SiteUpdateInterceptor();
	}
	// Interceptors
		// 매핑은 없고 인터셉터만 있는 경우, 인터셉터 작동 안할 수 있음
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/auth");
			registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout");
			registry.addInterceptor(siteUpdateInterceptor()).addPathPatterns("/admin/main/update");
			registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
					.excludePathPatterns(Arrays.asList("/user/auth", "/user/logout", "/assets/**", "/main/update"));
		}
}
