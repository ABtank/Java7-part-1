package ru.geekbrains.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthService;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

@Configuration
@Import(NewSpringConfig.class)  // импорт еще одной конфигурации
//@ComponentScan(basePackages = "ru.geekbrains.server")
@PropertySource(value="classpath:application.yaml") // в папке target
@PropertySource(value="classpath:application.properties") // в папке target
public class SpringConfig {

//    для application.properties
//    @Value("${database.class}")
//    private String driverClassName;
//    @Value("${database.url}")
//    private String databaseUrl;
//    @Value("${database.username}")
//    private String username;
//    @Value("${database.password}")
//    private String password;

//    Для application.yaml
    @Value("${class}")
    private String driverClassName;
    @Value("${url}")
    private String databaseUrl;
    @Value("${login}")
    private String username;
    @Value("${password}")
    private String password;

    @Value("${username}") // имя username подставляется не то
    private String user;

    @Bean
    public ChatServer chatServer (){
        System.out.println(driverClassName+" \n"+databaseUrl+" \n"+username+" \n"+password);
        System.out.println("username="+user);
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
        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(databaseUrl);
        return ds;
    }

    @Bean
    @Scope (ConfigurableBeanFactory.SCOPE_PROTOTYPE) //при попытке @Autowired будет создаваться новый экземпляр бина
    public ClientHandler clientHandler (String login, Socket socket) throws IOException {
        return new ClientHandler( login, socket, chatServer ());
    }


}
