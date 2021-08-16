package ru.abtank.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final Connection conn;

    @Autowired
    public UserRepository(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public UserRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    public void insert(User user) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into users(login, password, email) values (?, ?, ?);")) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.execute();
        }
    }

    public void update(User user) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET login=?, password=?, email=? where id= ?;")) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getId());
            stmt.execute();
        }
    }

    public User findByLogin(String login) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, login, password, email from users where login = ?")) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
        return new User(-1, "", "","");
    }
    public User findById(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, login, password, email from users where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
        return new User(-1, "", "","");
    }

    public boolean deleteById(Integer id)  {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE from users where id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException exception){
            return false;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, login, password, email from users");

            while (rs.next()) {
                res.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists users (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    login varchar(25),\n" +
                    "    password varchar(25),\n" +
                    "    unique index uq_login(login)\n" +
                    ");");
        }
    }
}
