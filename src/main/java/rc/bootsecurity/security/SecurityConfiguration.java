package rc.bootsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
      
    @Autowired
   private UserPrincipalDetailsService usrPrDtlServ;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
      // below is used for inmemory authentication
            //  auth.inMemoryAuthentication().withUser("sachin")
            //  .password(passwordEncoder().encode("sachin123"))
            // //  .roles("ADMIN")
            //  .authorities("ACCESS_TEST1","ACCESS_TEST2","ROLE_ADMIN")
            // //  .authorities("ACCESS_TEST1","ACCESS_TEST2")    //THIS IS USED FOR ROLE BASED AUTHORIZATION,GRANTED AUTHORITITES
            //  .and()
            //  .withUser("shivam")
            //  .password(passwordEncoder().encode("shivam123"))
            //  .roles("USER")
            //  .and()
            //  .withUser("manager")
            //  .password(passwordEncoder().encode("manager123"))
            // //  .roles("MANAGER") if you are using authorities then roles precedance will remove to show
            // //role we have to use it in authorities like ->ROLE_MANAGER
            //  .authorities("ACCESS_TEST1","ROLE_MANAGER");

            //this is used for our custom authentication provider
            auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
    .antMatchers("/index").permitAll()
    .antMatchers("/profile/**").authenticated()
    .antMatchers("/admin/**").hasRole("ADMIN")
    .antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
    .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")
    .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
    .antMatchers("/api/public/users").hasRole("ADMIN")
    .and()
    // .httpBasic();//for http based authenticatiom
    .formLogin()
    .loginProcessingUrl("/signin")
    .loginPage("/login")//this is spring provided url
    .permitAll()//for login
    .usernameParameter("txtUsername")//if we don't use name=txtUsername instd of name=username we don't need it
    .passwordParameter("txtPassword")//if we don't use name=txtPassword insted of name=password we don't need it
    .and()
    .logout()//this is for logout to perform
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//this is to map our route
                                                                // which we provide in our form in navbar action :post
    .logoutSuccessUrl("/login")//after succesful logout which page to visit.
    .and()
    .rememberMe()//this is for remember me functionality and 
                  // it also genreates cokies and inside cookiw we have a token(in hashform) store in web browser in the form of
                  // username+passeword(Encoded)+validation/expiration+secret key(all three are hashed)
    .tokenValiditySeconds(2592000)//custom seeting expiry
    .key("my secret!")//custom making of secret key
    .rememberMeParameter("checkRememberMe");//we have to use it cuz we use name="checkRememberMe",insted of name="remember-me"
    }
     
   @Bean
   DaoAuthenticationProvider authenticationProvider(){
     DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());//whenever we use custom db we have to set it
     daoAuthenticationProvider.setUserDetailsService(this.usrPrDtlServ);
     return daoAuthenticationProvider;
   }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}