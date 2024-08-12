package com.Training.BankingApp.user;

import com.Training.BankingApp.account.AccountRepository;
import com.Training.BankingApp.services.JWTService;
import com.Training.BankingApp.util.EncryptionUtil;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;
    String secretKey = "MySecretKey12345";


    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


//    public ResponseEntity<?> loginCustomerUpdated(@org.jetbrains.annotations.NotNull LoginRequest loginRequest) {
//        User user = userRepository.findOneByEmail(loginRequest.getEmail());
//        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
//        }
//        if ( !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
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

    public ResponseEntity<?> loginCustomer(@org.jetbrains.annotations.NotNull LoginRequest loginRequest) throws Exception {
        User user = userRepository.findOneByEmail(loginRequest.getEmail());

        if (user == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }
//        String decryptedPassword = EncryptionUtil.decryptPassword(loginRequest.getPassword(), secretKey);

        if ( !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        if (user.getRoleId() != 2L) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role");
        }

        String jwt = jwtService.generateToken(user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

        jwtAuthenticationResponse.setUserId(user.getUserId());
        jwtAuthenticationResponse.setRoleId(user.getRoleId());
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setEmail(user.getEmail());
        jwtAuthenticationResponse.setUsername(user.getUsername());
        jwtAuthenticationResponse.setPhoneNumber(user.getPhoneNumber());
        jwtAuthenticationResponse.setLoggedIn(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwt);

        return ResponseEntity.ok()
                .headers(headers)
                .body(jwtAuthenticationResponse);
    }


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
