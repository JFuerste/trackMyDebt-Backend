package com.jfuerste.trackmydebtbackend.bootstrap;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BootstrapData(PasswordEncoder passwordEncoder, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {
        //Only load data if none exists
        if (userRepository.count() == 0){
            loadData();
        }
    }

    private void loadData() {
        User user = new User();
        user.setEmail("j@fuerste.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setRole(User.Role.ADMIN);
        user.setDisplayName("Joshua");


        User user2 = User.builder()
                .displayName("Michael")
                .email("m@mutert.com")
                .password(passwordEncoder.encode("secret"))
                .role(User.Role.USER)
                .build();

        user.addFriend(user2);

        userRepository.save(user);
        userRepository.save(user2);

        Transaction transaction = Transaction.builder()
                .amount(2.5D)
                .receiver(user)
                .sender(user2)
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

        System.out.println("Loaded Test Data");
    }
}
