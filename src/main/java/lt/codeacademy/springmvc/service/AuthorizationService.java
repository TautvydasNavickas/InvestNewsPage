package lt.codeacademy.springmvc.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.springmvc.DTO.AuthResponse;
import lt.codeacademy.springmvc.DTO.LoginRequest;
import lt.codeacademy.springmvc.DTO.RegisterRequest;
import lt.codeacademy.springmvc.configuration.JwtProvider;
import lt.codeacademy.springmvc.exception.InvestAccountNotFoundException;
import lt.codeacademy.springmvc.repository.UserRepository;
import lt.codeacademy.springmvc.repository.VerificationRepository;
import lt.codeacademy.springmvc.repository.entity.EmailNotification;
import lt.codeacademy.springmvc.repository.entity.User;
import lt.codeacademy.springmvc.repository.entity.Verification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;
import static lt.codeacademy.springmvc.utility.Constant.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorizationService {

    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  VerificationRepository verificationRepository;
    private  SendingMailService sendingMailService;
    private  AuthenticationManager authenticationManager;
    private  MailMessageContentService mailMessageContentService;
    private JwtProvider jwtProvider;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);

        log.info("User Registered Successfully, Sending Authentication Email");
        String token = generateVerificationToken(user);
        String message = mailMessageContentService.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);

        sendingMailService.sendMail(new EmailNotification("Please Activate your account", user.getEmail(), message));
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

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthResponse(authenticationToken, loginRequest.getUsername());
    }

    public void verifyAccount(String token) {
        Optional<Verification> verificationOptional = verificationRepository.findByToken(token);
        verificationOptional.orElseThrow(() -> new InvestAccountNotFoundException("Invalid Token"));
        fetchUserAndEnable(verificationOptional.get());
    }

    @Transactional
    private void fetchUserAndEnable(Verification verification) {
        String username = verification.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvestAccountNotFoundException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
