package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args){

        UserService service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("John", "Rambo", (byte)67);
        service.saveUser("Arnold", "Schwarzenegger", (byte)76);
        service.saveUser("Donald", "Duck", (byte)112);
        service.saveUser("Jacki", "Chan", (byte)66);
        service.getAllUsers();
        service.dropUsersTable();

        Util.getSessionFactory().close();
    }
}
