package br.com.cast.castinside.config;

import br.com.cast.castinside.model.Role;
import br.com.cast.castinside.model.User;
import br.com.cast.castinside.repository.RoleRepository;
import br.com.cast.castinside.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<User> users = this.userRepository.findAll();

        if (users.isEmpty()) {
            createUsers("Maycon", "maycon_ribeiro@hotmail.com", this.passwordEncoder.encode("123456"), "ROLE_SUPERVISOR");
            createUsers("Maycon Augusto", "admin", this.passwordEncoder.encode("123456"), "ROLE_ADMIN");
        }
    }

    private void createUsers(String name, String email, String password, String role) {
        Role roleEntity = new Role(role);
        this.roleRepository.save(roleEntity);
        User user = new User(name, email, password, Arrays.asList(roleEntity));
        this.userRepository.save(user);
    }
}
