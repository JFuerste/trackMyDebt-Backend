package com.jfuerste.trackmydebtbackend.bootstrap;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("default")
public class BootstrapProdData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public BootstrapProdData(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Only load data if none exists
        if (userRepository.count() == 0){
            loadData();
        }
    }

    private void loadData() {
        User user = new User();
        user.setEmail("j@fuerste.com");
        user.setPassword(passwordEncoder.encode("supersecretproductionpassword"));
        user.setRole(User.Role.ADMIN);
        user.setDisplayName("Joshua");

        userRepository.save(user);


        System.out.println("Loaded Prod Data");
    }
}
