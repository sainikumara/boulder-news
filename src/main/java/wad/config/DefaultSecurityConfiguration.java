package wad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("default")
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // mahdollistetaan h2-konsolin käyttö
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/articles/**").permitAll()
                .antMatchers("/writers/**").permitAll()
                .antMatchers("/categories/**").permitAll()
                .antMatchers("/pictures/**").permitAll()
                .antMatchers("/pictures_by_article/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().permitAll().defaultSuccessUrl("/admin_panel",true).and()
                .logout().permitAll().logoutSuccessUrl("/");
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("bouldergod").password("grade").roles("USER");
    }
}
