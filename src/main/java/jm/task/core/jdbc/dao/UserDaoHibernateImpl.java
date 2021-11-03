package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(
      "CREATE TABLE IF NOT EXISTS user(" +
                    "id bigint not null auto_increment, " +
                    "name varchar(45) not null, " +
                    "lastName varchar(45) not null, " +
                    "age tinyint not null, " +
                    "primary key (id) " +
                ");").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if(transaction !=null){
                transaction.rollback();
                System.out.println("===== CREATE SESSION ERROR =====\n");
                ex.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
                session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE user").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            System.out.println("Catch block");
            if(transaction !=null){
                transaction.rollback();
                System.out.println("===== DROP SESSION ERROR =====\n");
                ex.printStackTrace();
            }
        } finally {
                session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (Exception ex) {
            if(transaction !=null){
                transaction.rollback();
                System.out.println("===== SAVE SESSION ERROR =====\n");
                ex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete("User", id);
            transaction.commit();
        } catch (Exception ex) {
            if(transaction !=null){
                transaction.rollback();
                System.out.println("===== REMOVE SESSION ERROR =====\n");
                ex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            users = session.createQuery("from User").list();
            transaction.commit();
            System.out.println(users);
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
                System.out.println("===== GET SESSION ERROR =====\n");
                ex.printStackTrace();
            }
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate table user").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
                System.out.println("===== GET SESSION ERROR =====\n");
                ex.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}