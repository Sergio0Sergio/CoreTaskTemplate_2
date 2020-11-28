package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();
    private final String createTable = "CREATE TABLE if not exists USERS "
            + "(ID BIGINT not NULL AUTO_INCREMENT, "
            + "USERNAME VARCHAR(50) not NULL, "
            + "USERLASTNAME VARCHAR(50) not NULL, "
            + "USERAGE TINYINT not NULL, "
            + " PRIMARY KEY (id))";
    private final String insertUser = "INSERT INTO USERS (USERNAME,USERLASTNAME,USERAGE) VALUES(?,?,?)";
    private final String dropTable = "DROP TABLE if exists USERS ";
    private final String cleanTable = "TRUNCATE TABLE USERS";
    private final String getAllUsers = "SELECT * FROM USERS";
    private final String deleteUserById = "DELETE FROM USERS WHERE id=?";

    public void createUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStmt = connection.prepareStatement(insertUser)) {
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, lastName);
            preparedStmt.setByte(3, age);
            preparedStmt.executeUpdate();
            System.out.println("добавлен в базу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStmt = connection.prepareStatement(deleteUserById)) {
            preparedStmt.setLong(1, id);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User user;
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(getAllUsers);
            while (result.next()) {
                user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("USERNAME"));
                user.setLastName(result.getString("USERLASTNAME"));
                user.setAge(result.getByte("USERAGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}