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
    	   http.authorizeRequests().antMatchers("/aprovarPartida").hasAnyRole("ADMIN").and().formLogin().permitAll();
               return http.build();
       }

       @Bean
       public UserDetailsService userDetailsService() {
           UserDetails mmofer = User.withDefaultPasswordEncoder()
                   .username("mmofer")
                   .password("mmofer756")
                   .roles("USER")
                   .build();
           UserDetails mmcard = User.withDefaultPasswordEncoder()
                   .username("mmcard")
                   .password("mmcard578")
                   .roles("USER")
                   .build();
           UserDetails grimm = User.withDefaultPasswordEncoder()
                   .username("grimm")
                   .password("grimm426")
                   .roles("USER")
                   .build();
           UserDetails bigga = User.withDefaultPasswordEncoder()
                   .username("bigga")
                   .password("bigga874")
                   .roles("USER")
                   .build();
           UserDetails deumelibre = User.withDefaultPasswordEncoder()
                   .username("deumelibre")
                   .password("FDcmd338")
                   .roles("ADMIN", "USER")
                   .build();
           return new InMemoryUserDetailsManager(mmofer, mmcard, grimm, bigga, deumelibre);
       }
       
       @Bean
       public SpringSecurityDialect securityDialect() {
           return new SpringSecurityDialect();
       }

       // Possibly more bean methods ...
}
