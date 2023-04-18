package ma.emsi.jpa.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.emsi.jpa.entities.Role;
import ma.emsi.jpa.entities.User;
import ma.emsi.jpa.repositories.RoleRepository;
import ma.emsi.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service //Ca vous dir c'est un component de la couche métier
@Transactional //Ca vous dir les transactions va délegué a spring
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = findUserByUserName(userName);
        Role role = findRoleByRoleName(roleName);
        if(user.getRoles() != null){
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
        //userRepository.save(user);  //Pas nésecaire pour se cas la
    }

    @Override
    public User authenticate(String userName, String passWord) {
        User user = userRepository.findByUserName(userName);
        if (user == null) throw new RuntimeException("Bad Credentials");
        if(user.getPassWord().equals(passWord)){
            return user;
        }
        throw new RuntimeException("Bad Credentials");
    }
}
