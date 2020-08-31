package com.jfuerste.trackmydebtbackend.bootstrap;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.TransactionRepository;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
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

        User user = new User();
        user.setEmail("j@fuerste.com");
        user.setPassword(passwordEncoder.encode("secret"));
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        User user2 = User.builder()
                .email("m@mutert.com")
                .password(passwordEncoder.encode("secret"))
                .role(User.Role.USER)
                .build();
        userRepository.save(user2);

        Transaction transaction = Transaction.builder()
                .amount(2.5D)
                .receiver(user)
                .sender(user2)
                .timestamp(LocalDate.now())
                .build();
        transactionRepository.save(transaction);

        System.out.println("Loaded Test Data");


    }
}
