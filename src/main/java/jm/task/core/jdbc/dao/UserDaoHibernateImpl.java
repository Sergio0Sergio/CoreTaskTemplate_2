package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private final Util util = new Util();

    @Override
    public void createUsersTable() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if not exists USERS "
                    + "(ID BIGINT not NULL AUTO_INCREMENT, "
                    + "USERNAME VARCHAR(50) not NULL, "
                    + "USERLASTNAME VARCHAR(50) not NULL, "
                    + "USERAGE TINYINT not NULL, "
                    + " PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if exists users ").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println(" User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }


    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete  User  where id = :param");
            query.setParameter("param", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<User> userList = session.createSQLQuery("SELECT * FROM users")
                    .addEntity(User.class)
                    .list();
            transaction.commit();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}