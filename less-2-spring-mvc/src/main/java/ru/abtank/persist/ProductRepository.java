package ru.abtank.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final Connection conn;

    @Autowired
    public ProductRepository(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public ProductRepository(Connection conn) {
        this.conn = conn;
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into products(name, description, price) values (?, ?, ?);")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getPrice());
            stmt.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE products SET name=?, description=?, price=? where id= ?;")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getPrice());
            stmt.setInt(4, product.getId());
            stmt.execute();
        }
    }

    public Product findByName(String name) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, name, description, price from products where name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
        return new Product(-1, "", "","");
    }
    public Product findById(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, name, description, price from products where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
        return new Product(-1, "", "","");
    }

    public boolean deleteById(Long id)  {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE from products where id = ?")) {
            stmt.setLong(1, id);
            stmt.execute();
            return true;
        } catch (SQLException exception){
            return false;
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name, description, price from products");

            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }
        return res;
    }

}
