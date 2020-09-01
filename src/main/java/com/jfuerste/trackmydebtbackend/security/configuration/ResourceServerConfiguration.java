package com.jfuerste.trackmydebtbackend.security.configuration;

import com.jfuerste.trackmydebtbackend.errors.CustomAccessDeniedHandler;
import com.jfuerste.trackmydebtbackend.errors.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@EnableResourceServer
@EnableAuthorizationServer
@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public ResourceServerConfiguration(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/oauth/authorize").permitAll()
                .antMatchers("/api/v1/signin*").permitAll()
                .antMatchers("/api/v1/signin/**").permitAll()
                .antMatchers("/api/v1/users").authenticated()
                .antMatchers("/api/v1/users/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers("oauth2/callback/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(new CustomAccessDeniedHandler());

    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

}
