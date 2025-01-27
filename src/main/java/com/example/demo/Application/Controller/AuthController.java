/* package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.User;
import com.example.demo.Application.Service.UserService;
import com.example.demo.Application.Repository.UserRepository;
import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Config.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setEnabled(updatedUser.isEnabled());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setEmail(updatedUser.getEmail());
        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            securityContextRepository.saveContext(securityContext, request, response);

            String token = tokenProvider.generateToken(authentication);

            User user = userRepository.findByUsername(loginUser.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("User not found");
            }

            LoginResponse loginResponse = new LoginResponse(token, user);

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
*/

package com.example.demo.Application.Controller;

import com.example.demo.Application.Model.User;
import com.example.demo.Application.Service.UserService;
import com.example.demo.Application.Service.EmailService;
import com.example.demo.Application.Repository.UserRepository;
import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Config.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    /*@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setVerificationToken(UUID.randomUUID().toString());
            user.setVerified(false);
            userRepository.save(user);

            String verificationLink = "http://localhost:9090/api/auth/verify?token=" + user.getVerificationToken();
            emailService.sendEmail(user.getEmail(), "Verify your account",
                    "Please verify your account using the following link: " + verificationLink);

            return ResponseEntity.ok("User registered successfully. Please check your email to verify your account.");
        } catch (Exception e) {
            // Log completo para depuración
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Codificar la contraseña
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setVerificationToken(UUID.randomUUID().toString());
            user.setVerified(false);

            // Asegurarnos de que el campo 'imageIdentifier' sea asignado
            if (user.getImageIdentifier() == null || user.getImageIdentifier().isEmpty()) {
                user.setImageIdentifier(UUID.randomUUID().toString()); // Generar un identificador único para la imagen
            }

            userRepository.save(user);

            String verificationLink = "http://localhost:9090/api/auth/verify?token=" + user.getVerificationToken();
            emailService.sendEmail(user.getEmail(), "Verify your account",
                    "Please verify your account using the following link: " + verificationLink);

            return ResponseEntity.ok("User registered successfully. Please check your email to verify your account.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
        user.setVerified(true);
        user.setVerificationToken(null); // Eliminar el token después de la verificación
        userRepository.save(user);
        return ResponseEntity.ok("Account verified successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            securityContextRepository.saveContext(securityContext, request, response);

            String token = tokenProvider.generateToken(authentication);

            User user = userRepository.findByUsername(loginUser.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("User not found");
            }

            if (!user.isVerified()) {
                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("Please verify your account before logging in.");
            }

            LoginResponse loginResponse = new LoginResponse(token, user);

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid username or password");
        }
    }
}