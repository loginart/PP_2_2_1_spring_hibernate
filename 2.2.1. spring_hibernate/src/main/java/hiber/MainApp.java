package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User vasya = new User("Vasya", "Vasechkin", "vasechkin@mail.io");
      User petya = new User("Petya", "Sidorov", "sidorov@mail.io");
      User olga = new User("Olga", "Petrova", "petrova@mail.io");
      User sveta = new User("Svetlana", "Ivanova", "ivanova@mail.io");

      Car lada = new Car("Lada", 2010);
      Car volvo = new Car("volvo", 2011);
      Car nissan = new Car("nissan", 2012);
      Car honda = new Car("civic", 8);

      userService.add(vasya.setCar(lada).setUser(vasya));
      userService.add(petya.setCar(volvo).setUser(petya));
      userService.add(olga.setCar(nissan).setUser(olga));
      userService.add(sveta.setCar(honda).setUser(sveta));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+ user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }
      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      System.out.println(userService.getUserByCar("Lada", 2010));

      try {
         User notFoundUser = userService.getUserByCar("GAZ", 4211);
      } catch (NoResultException e) {
         System.out.println("User not found");
      }

      context.close();
   }
}
