package com.example.SpringSecurityapp.configs;

import com.example.SpringSecurityapp.models.User;
import com.example.SpringSecurityapp.servis.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // включаем безопастность
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userServiceImpl) {
        this.successUserHandler = successUserHandler;
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    private DataSource dataSource;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/authenticated/**").authenticated()
                //                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                //                .logoutUrl("/logout")
                //                .logoutSuccessUrl("/")
                .permitAll();


//                .httpBasic().and()
//                .csrf().disable()// защита от угрозы
//                .authorizeHttpRequests()
//                .antMatchers("/authenticated/**").authenticated() //сюда пускаем тока аутенфицированных, в остальные вход свободный
//                .and()
//                .formLogin().loginProcessingUrl("/login")// дизайн формы аутенфикации, можно добавить и свою. Аналог .httpBasic()
//                .and()
//                .logout().logoutSuccessUrl("/");

        return httpSecurity.build();
    }

    // in-Memory храним в памяти
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = // минимальная информация опользователя
//                User.builder()
//                        .username("u")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("a")
//                        .password("2")
//                        .roles("ADMIN", "USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }


    // jdbcAuthenticated - храним базе, в какихто таблицах (плохо управлять)

//    @Bean // если бину нужно доступ к БД то DataSource dataSource
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        // кладем в базу данных пользователей
//        UserDetails user =
//                User.builder()
//                        .username("u")
//                        .password("1")
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.builder()
//                        .username("a")
//                        .password("2")
//                        .roles("ADMIN", "USER")
//                        .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        // и положи через проверку, если сущность есть то его надо удалить : "не обязательно"
////        if (users.userExists(user.getUsername())){
////            users.deleteUser(user.getUsername());
////        }
////        if (users.userExists(admin.getUsername())){
////            users.deleteUser(admin.getUsername());
////        }
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }


    // пользуемся нашими таблицами

    // Преобразователь паролей, все пароли будут проходить через метод
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // мы передали в AuthenticationProvider логин пароль, а его задача сказать - существует такой пользователь или нет^ если да по толожить в SpringSecurityContest
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider aP = new DaoAuthenticationProvider();
        aP.setPasswordEncoder(passwordEncoder());
        // узнает если такой пользователь или нет, отдельно составим запрос
        aP.setUserDetailsService(userServiceImpl);
        return aP;
    }

}