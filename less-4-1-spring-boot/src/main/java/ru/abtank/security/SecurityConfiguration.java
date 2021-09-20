package ru.abtank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.abtank.persist.repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;

//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true)  //включаем защиту на уровне метод
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserAuthService(userRepository);
    }


    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth
            , UserDetailsService userDetailsService
            , PasswordEncoder passwordEncoder
    ) throws Exception {
//         -----DAO auth----
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);

//        создание пользователя в in memory при запуске системы
        auth.inMemoryAuthentication()
                .withUser("in_user")
                .password(passwordEncoder.encode("123")) // генерим пароль https://bcrypt-generator.com/
                .roles("ADMIN")
                ;

    }

    //    определяем области доступа через внутренний клас (можно и без него через наследования напрямую)
    @Configuration
    @Order(2) //порядок загрузки
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll()  // доступ для всех
                    .antMatchers("/css/*").permitAll()
                    .antMatchers("/js/*").permitAll()
                    .antMatchers("/font-awesome/*").permitAll()
                    .antMatchers("/plugins/*").permitAll()
//                    .antMatchers("/").anonymous()  // доступ для всех не авторизованных
//        .antMatchers("/user/**").authenticated()  // всем авторизованным
                    .antMatchers("/exercise/**").authenticated()
                    .antMatchers("/user/**").hasRole("ADMIN")  // ограничение по роли
                    .antMatchers("/role/**").hasAnyRole("ADMIN", "USER")  // ограничение по роли
                    .and()
                    .formLogin();
        }

    }

    // пока не получается чтоб работали оба адаптера разом.. Кто первый тот и работает )))
    //    настройка области доступак к REST API
    @Configuration
    @Order(1)
    public static class APIWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()  //начинаем описывать конфогурацию
                    .antMatchers("/api/**").hasRole("ADMIN")  // доступ для всех
                    .and()
                    .httpBasic()//базовая авторизация
                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, exception) -> {  // обработчик ошибок при авторизации
                        httpServletResponse.setContentType("application/json");                          // вывод ошибки в формате json, а не редиректило на html страницу
                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        httpServletResponse.getWriter().println("{\"error\": \"" + exception.getMessage() + "\" }");
                    })
                    .and()
                    .csrf().disable()// отключаем проверку csrf так как для REST сервисов она не нужна. (так как нет сессии (только State Less))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }
}
