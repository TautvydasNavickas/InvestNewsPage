package lt.codeacademy.springmvc.service;

import lt.codeacademy.springmvc.DTO.RegisterRequest;
import lt.codeacademy.springmvc.repository.UserRepository;
import lt.codeacademy.springmvc.repository.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static java.time.Instant.now;


public class AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
