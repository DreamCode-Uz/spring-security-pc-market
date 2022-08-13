package uz.pdp.springsecuritypcmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails superAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("SUPER_ADMIN")
                .authorities("READ", "READ_ALL", "DELETE", "ADD", "EDIT")
                .build();
        UserDetails moderator = User.builder()
                .username("moderator")
                .password(passwordEncoder().encode("moderator"))
                .roles("MODERATOR")
                .authorities("READ", "READ_ALL", "ADD", "EDIT")
                .build();
        UserDetails operator = User.builder()
                .username("operator")
                .password(passwordEncoder().encode("operator"))
                .roles("OPERATOR")
                .authorities("READ", "READ_ALL")
                .build();

        return new InMemoryUserDetailsManager(superAdmin, moderator, operator);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("READ", "READ_ALL")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("DELETE")
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority("EDIT")
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADD")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}

//  'org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter' is deprecated