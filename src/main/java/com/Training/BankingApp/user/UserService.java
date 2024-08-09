package com.Training.BankingApp.user;

import com.Training.BankingApp.account.AccountRepository;
import com.Training.BankingApp.services.JWTService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;



//    public UserService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    public ResponseEntity<?> loginCustomer(@NotNull LoginRequest loginRequest) {
        User user = userRepository.findOneByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        if (user.getRoleId() != 2L) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role");
        }

        String jwt = jwtService.generateToken(user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUserId(user.getUserId());
        jwtAuthenticationResponse.setRoleId(user.getRoleId());
        jwtAuthenticationResponse.setEmail(user.getEmail());
        jwtAuthenticationResponse.setUsername(user.getUsername());
        jwtAuthenticationResponse.setPhoneNumber(user.getPhoneNumber());
        jwtAuthenticationResponse.setLoggedIn(true);

        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

//    public ResponseEntity<?> loginCustomerUpdated(@org.jetbrains.annotations.NotNull LoginRequest loginRequest) {
//        User user = userRepository.findOneByEmail(loginRequest.getEmail());
//
//        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//        if (user.getRoleId() != 2L) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role");
//        }
//
//        String jwt = jwtService.generateToken(user);
//
//        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
//        jwtAuthenticationResponse.setToken(jwt);
//        jwtAuthenticationResponse.setUserId(user.getUserId());
//        jwtAuthenticationResponse.setRoleId(user.getRoleId());
//        jwtAuthenticationResponse.setEmail(user.getEmail());
//        jwtAuthenticationResponse.setUsername(user.getUsername());
//        jwtAuthenticationResponse.setPhoneNumber(user.getPhoneNumber());
//        jwtAuthenticationResponse.setLoggedIn(true);
//
//        return ResponseEntity.ok(jwtAuthenticationResponse);
//    }

    public ResponseEntity<?> loginAdmin(@NotNull LoginRequest loginRequest) {
        User user = userRepository.findOneByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        if (user.getRoleId() != 1L) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role");
        }

        String jwt = jwtService.generateToken(user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setUserId(user.getUserId());
        jwtAuthenticationResponse.setRoleId(user.getRoleId());
        jwtAuthenticationResponse.setEmail(user.getEmail());
        jwtAuthenticationResponse.setUsername(user.getUsername());
        jwtAuthenticationResponse.setPhoneNumber(user.getPhoneNumber());
        jwtAuthenticationResponse.setLoggedIn(true);

        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
            }
        };
    }

    public User getUserByUserId(long userId) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
}
