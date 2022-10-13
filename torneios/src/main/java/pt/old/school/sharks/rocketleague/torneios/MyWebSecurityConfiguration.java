package pt.old.school.sharks.rocketleague.torneios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfiguration {

       @Bean
       public WebSecurityCustomizer webSecurityCustomizer() {
               return (web) -> web.ignoring()
               // Spring Security should completely ignore URLs starting with /resources/
                               .antMatchers("/resources/**");
       }

       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	   http.authorizeRequests().antMatchers("/resultados").hasAnyRole("USER").and().formLogin().permitAll();
    	   http.authorizeRequests().antMatchers("/criarPartida").hasAnyRole("ADMIN").and().formLogin().permitAll();
               return http.build();
       }

       @Bean
       public UserDetailsService userDetailsService() {
               UserDetails user = User.withDefaultPasswordEncoder()
                       .username("user")
                       .password("password")
                       .roles("USER")
                       .build();
               UserDetails admin = User.withDefaultPasswordEncoder()
                       .username("admin")
                       .password("password")
                       .roles("ADMIN", "USER")
                       .build();
               return new InMemoryUserDetailsManager(user, admin);
       }
       
       @Bean
       public SpringSecurityDialect securityDialect() {
           return new SpringSecurityDialect();
       }

       // Possibly more bean methods ...
}
