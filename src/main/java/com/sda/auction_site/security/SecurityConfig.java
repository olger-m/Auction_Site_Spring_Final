package com.sda.auction_site.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.auction_site.model.AppUser;
import com.sda.auction_site.model.Role;
import com.sda.auction_site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {



    @Autowired
    private UserService userService;

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("PUT","POST","DELETE", "OPTION","GET"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;

    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){


        return ((request, response, authentication) -> {
            if(authentication != null){

                AppUser appUser = (AppUser) authentication.getPrincipal();

                Map<String, String> userInfomap = new HashMap<>();

                userInfomap.put("username", appUser.getUsername());
                userInfomap.put("id", appUser.getId().toString());
                userInfomap.put( "role",appUser.getRoleSet().stream().map(Role::getRoleName).toList().get(0));

                response.getWriter().write(new ObjectMapper().writeValueAsString(userInfomap));
                response.setStatus(200);
            }
        });
    }

    @Bean
    LogoutSuccessHandler  logoutSuccessHandler (){
        return ((request, response, authentication) ->{


            Map<String,Boolean> logOutMap = new HashMap<>();

            logOutMap.put("logged_out",true);

            response.getWriter().write(new ObjectMapper().writeValueAsString(logOutMap));
            response.setStatus(200);
        } );
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        HttpSecurity http = httpSecurity.cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authz) -> authz.requestMatchers("/biddingapp/v1/login",
                        "/biddingapp/v1/logout","/biddingapp/v1/register","/biddingapp/v1/createauction","/biddingapp/v1/data/**")
                .permitAll()
                .requestMatchers("/biddingapp/v1/admin/**")
                .hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated());

        http.userDetailsService(userService);

        http.formLogin((form) -> form.loginPage("/biddingapp/v1/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler()));



        http.logout((logout)->logout.logoutUrl("/biddingapp/v1/logout")
                .logoutSuccessHandler(logoutSuccessHandler()));


        http.httpBasic(Customizer.withDefaults());

        return http.build();









    }

}
