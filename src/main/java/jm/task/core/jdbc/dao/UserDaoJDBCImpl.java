package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
    private String query = "";

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        query =
            "CREATE TABLE IF NOT EXISTS user(" +
                "id bigint not null auto_increment, " +
                "name varchar(45) not null, " +
                "lastName varchar(45) not null, " +
                "age tinyint not null, " +
                "primary key (id) " +
            ");";
        try(Connection connection = util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException ex) {
            //ignore
        }
    }

    public void dropUsersTable() {
        query = "drop table user;";
        try(Connection connection = util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException ex) {
            //ignore
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        query = "insert into  user(name, lastName, age) values(?, ?, ?)";
        try(Connection connection = util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (SQLException ex) {
            System.out.println("Error during creation user\n" + ex);
        }
    }

    public void removeUserById(long id) {
        query = "delete from user where id = ?;";
        try(Connection connection = util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Error during removing user\n" + ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        query = "select * from user";
        try(Connection connection = util.getConnection()) {
            ResultSet res = connection.createStatement().executeQuery(query);
            User user = null;
            while (res.next()) {
                user = new User(
                        res.getString("name"),
                        res.getString("lastName"),
                        (byte)res.getInt("age"));
                System.out.println(user);
            }
            if(user != null) {list.add(user);}
        } catch (SQLException ex) {
            System.out.println("Error during getting data\n" + ex);
        }
    return list;
    }

    public void cleanUsersTable() {
        query = "delete from user";
        try(Connection connection = util.getConnection()) {
            connection.createStatement().execute(query);
        } catch (SQLException ex) {
            System.out.println("Error during cleaning operation\n" + ex);
        }
    }
}
