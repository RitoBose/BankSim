package com.rbose.onlinebanking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.rbose.onlinebanking.repository.UserDao;
import com.rbose.onlinebanking.service.UserServiceImpl.UserSecurityServiceImpl;

import java.security.SecureRandom;

/**
 * Created by Spring Tool Suite 4.
 * Project : online-banking
 * User: RitoBose
 * Email: ritankarbose2004@gmail.com
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String SALT = "salt"; // Salt should be protected carefully
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**",
            "/console/**",
            "/signup"
    };

    private final UserDao userRepository;

    public SecurityConfig(UserDao userRepository) {
        this.userRepository = userRepository;
    }

//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return authenticationManagerBean();
//    }

    @Bean
     BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsServiceBean) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())  // ✅ Fixed Lambda DSL format
            .cors(cors -> cors.disable())  // ❗ Consider configuring CORS instead of disabling
            .formLogin(formLogin -> formLogin
                .loginPage("/index").permitAll()
                .defaultSuccessUrl("/userFront", true)
                .failureUrl("/index?error")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index?logout")  // ✅ Removed duplicate logout URL
                .deleteCookies("remember-me")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe  // ✅ Fixed `.rememberMe()` syntax
                .userDetailsService(userDetailsServiceBean)
            );

        return http.build();
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
//        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder);
//    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceBean());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    UserDetailsService userDetailsServiceBean() {
        return new UserSecurityServiceImpl(userRepository);
    }

}
