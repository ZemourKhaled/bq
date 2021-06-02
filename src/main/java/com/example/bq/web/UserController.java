package com.example.bq.web;


import com.example.bq.model.AuthTokenRepository;
import com.example.bq.model.User;
import com.example.bq.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${com.exemple.auth.token}")
    private String authToken;

    @Value("${com.exemple.auth.expired}")
    private int expiredTime;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/current")
    ResponseEntity<User> getUserConnected(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserConnected(@PathVariable("id") Integer id) {
        User user = userRepository.findById(id).orElse(new User());
        return ResponseEntity.ok().body(user);
    }

}