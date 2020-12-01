package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args){
        UserDao userDao = new UserDaoJDBCImpl();
       //userDao.createUsersTable();
       // userDao.saveUser("asdfasdf", "asgasfdgasdf", (byte) 44);
       // userDao.saveUser("aswerdf", "asgdf", (byte) 22);
       // userDao.removeUserById(1L);
       //userDao.dropUsersTable();


    }

}
