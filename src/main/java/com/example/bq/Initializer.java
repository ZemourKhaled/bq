package com.example.bq;

import com.example.bq.model.User;
import com.example.bq.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Initializer implements CommandLineRunner{

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Initializer(UserRepository rep, BCryptPasswordEncoder bCrypt){
        this.repository = rep;
        this.bCryptPasswordEncoder = bCrypt;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("test", "test@test.fr", this.bCryptPasswordEncoder.encode("test"));
        repository.save(user);
    }
}