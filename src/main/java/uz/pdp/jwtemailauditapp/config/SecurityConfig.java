package uz.pdp.jwtemailauditapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/register","/feedback").permitAll()
                .anyRequest().permitAll();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
//        mailSender.setProtocol("smtp");
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("testforjavaspring@gmail.com");
        mailSender.setPassword("4606044i");
        Properties properties=mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.debug","true");
//        properties.setProperty("protocol","smtp");
//        properties.put("mail.smtp.timeout", 5000);
//        properties.put("mail.smtp.connectiontimeout", 5000);
//        properties.put("mail.smtp.writetimeout", 5000);
//        properties.put("mail.smtps.ssl.checkserveridentity",true);
//        properties.put("mail.smtps.ssl.trust","*");
        System.out.println(mailSender);
        return mailSender;
    }
}
