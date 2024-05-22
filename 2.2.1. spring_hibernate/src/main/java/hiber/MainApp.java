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


      User katy = new User("Katy", "Petrova","katypetrova@mail.ru");
      User pavel = new User("Pavel", "Gusev","pavelgusev@gmail.ru");
      User alexey = new User("Alexey", "Ivanov","alexivanov@yandex.ru");
      User olga = new User("Olga", "Sidorova","olgasidorova@gmail.ru");


      Car honda = new Car("Honda", 8);
      Car bmw = new Car("BMW", 6);
      Car ford = new Car("Ford", 3);
      Car toyota = new Car("Toyota", 1);

      userService.add(katy.setCar(honda).setUser(katy));
      userService.add(pavel.setCar(bmw).setUser(pavel));
      userService.add(alexey.setCar(ford).setUser(alexey));
      userService.add(olga.setCar(toyota).setUser(olga));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car = " + user.getCar());
         System.out.println();
      }

      //пользователи с автомобилями

      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      //достать юзеров по модели и серии автомобиля

      System.out.println(userService.getUserByCar("Toyota", 1).toString());
      System.out.println(userService.getUserByCar("Honda", 8).toString());


      try {
         User notFoundUser = userService.getUserByCar("Lada", 15);
      } catch (NoResultException e) {
      }


      context.close();
   }
}
