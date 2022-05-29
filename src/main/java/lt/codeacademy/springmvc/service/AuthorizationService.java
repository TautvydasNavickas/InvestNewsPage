package lt.codeacademy.springmvc.service;

import lt.codeacademy.springmvc.DTO.RegisterRequest;
import lt.codeacademy.springmvc.repository.UserRepository;
import lt.codeacademy.springmvc.repository.VerificationRepository;
import lt.codeacademy.springmvc.repository.entity.User;
import lt.codeacademy.springmvc.repository.entity.Verification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.UUID;

import static java.time.Instant.now;

@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;

    public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationRepository verificationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationRepository = verificationRepository;
    }

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        Verification verification = new Verification();
        verification.setToken(token);
        verification.setUser(user);
        verificationRepository.save(verification);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
