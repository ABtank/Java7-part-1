package ru.geekbrains.server;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

@Configuration
//@ComponentScan(basePackages = "ru.geekbrains.server")
public class SpringConfig {
    @Bean
    public ChatServer chatServer (){
        return new ChatServer();
    }

    @Bean
    public AuthService authService (UserRepository userRepository){
        return new AuthServiceJdbcImpl(userRepository);
    }

    @Bean
    public UserRepository userRepository (DataSource dataSource) throws SQLException {
        return new UserRepository(dataSource);
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("12345678");
        ds.setUrl("jdbc:mysql://localhost:3306/network_chat?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        return ds;
    }

    @Bean
    @Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE) //при попытке @Autowired будет создаваться новый экземпляр бина
    public ClientHandler clientHandler (String login, Socket socket) throws IOException {
        return new ClientHandler( login, socket, chatServer ());
    }


}
