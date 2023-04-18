package ma.emsi.jpa;

import ma.emsi.jpa.entities.Role;
import ma.emsi.jpa.entities.User;
import ma.emsi.jpa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.stream.Stream;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            User user1 = new User();
            user1.setUserName("user1");
            user1.setPassWord("12345");
            userService.addNewUser(user1);

            User user2 = new User();
            user2.setUserName("admin");
            user2.setPassWord("12345");
            userService.addNewUser(user2);

            Stream.of("STUDENT", "USER", "ADMIN").forEach(r->{
                Role role1 = new Role();
                role1.setRoleName(r);
                userService.addNewRole(role1);
            });

            userService.addRoleToUser("user1", "STUDENT");
            userService.addRoleToUser("user1", "USER");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("admin", "ADMIN");

            try {
                User user=userService.authenticate("user1", "12345");
                System.out.printf(user.getUserId()+"\n");
                System.out.printf(user.getUserName()+"\n");
                System.out.printf("Roles ==> "+"\n");
                user.getRoles().forEach(r->{
                    System.out.printf("Role: " + r.toString()+"\n");
                });
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }
}
