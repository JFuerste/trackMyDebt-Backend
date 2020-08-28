package com.jfuerste.trackmydebtbackend;

import com.jfuerste.trackmydebtbackend.security.User;
import com.jfuerste.trackmydebtbackend.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public BootstrapData(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("j@fuerste.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

    }
}
