package ru.abtank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth) throws Exception {
//        1) создание пользователя в in memory при запуске системы
        auth.inMemoryAuthentication()
                .withUser("in_user")
                .password("{bcrypt}$2a$12$8QkN3vuU.Gk27B0YOuLne.9mSdWmmA4UiR5yH6uRcILR50GTt1K2q") // генерим пароль https://bcrypt-generator.com/
                .roles("ADMIN")
                .and()
                .withUser("in_disp")
                .password("{bcrypt}$2a$12$8QkN3vuU.Gk27B0YOuLne.9mSdWmmA4UiR5yH6uRcILR50GTt1K2q") // генерим пароль https://bcrypt-generator.com/
                .roles("DISP");
    }

    //    определяем области доступа через внутренний клас (можно и без него через наследования напрямую)
    @Configuration
    @Order(1) //порядок загрузки
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()  //начинаем описывать конфогурацию
                    .antMatchers("/").permitAll()  // доступ для всех
//                    .antMatchers("/").anonymous()  // доступ для всех не авторизованных
//        .antMatchers("/user/**").authenticated()  // всем авторизованным
                    .antMatchers("/user/**").hasRole("ADMIN")  // ограничение по роли
                    .antMatchers("/product/**").hasAnyRole("ADMIN", "DISP")  // ограничение по роли
                    .and()
                    .formLogin();
        }

    }
// пока не получается чтоб работали оба адаптера разом.. Кто первый тот и работает )))
    //    настройка области доступак к REST API
    @Configuration
    @Order(2)
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
                        httpServletResponse.getWriter().println("{\"error\": \""+exception.getMessage() + "\" }");
                    })
            .and()
            .csrf().disable()// отключаем проверку csrf так как для REST сервисов она не нужна. (так как нет сессии (только State Less))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }
}
