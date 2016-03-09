package jp.keio.jfn.wat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Created by jfn on 2/23/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

            http.authorizeRequests()
                    .antMatchers("/resources/**", "/testAll*").permitAll()
                    .antMatchers("/testAdmin*").hasRole("ADMIN")
                    .antMatchers("/testAnnotate*").access("hasRole('ADMIN') or hasRole('ANNOTATE')")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.xhtml")
                    .permitAll()
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/index.jsf");

    }
}
