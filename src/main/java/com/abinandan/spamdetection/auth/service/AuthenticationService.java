package com.abinandan.spamdetection.auth.service;

import com.abinandan.spamdetection.app.service.GlobalContactService;
import com.abinandan.spamdetection.auth.dto.AuthenticationRequest;
import com.abinandan.spamdetection.auth.dto.AuthenticationResponse;
import com.abinandan.spamdetection.auth.dto.RegisterRequest;
import com.abinandan.spamdetection.auth.models.Role;
import com.abinandan.spamdetection.auth.models.User;
import com.abinandan.spamdetection.auth.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GlobalContactService globalContactService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<User> userOptional =
                userRepository.findByPhoneNumber(registerRequest.getPhoneNumber());
        if (userOptional.isPresent()) {
            throw new RuntimeException("User with mobile number Already Exists. Try Logging In");
        }
        var user =
                User.builder()
                        .firstName(registerRequest.getFirstName())
                        .lastName(registerRequest.getLastName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .phoneNumber(registerRequest.getPhoneNumber())
                        .role(Role.USER)
                        .build();
        globalContactService.syncGlobalContactRepository(
                user.getPhoneNumber(), user.getEmail(), user.getFirstName());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getPhoneNumber(),
                        authenticationRequest.getPassword()));
        var user =
                userRepository
                        .findByPhoneNumber(authenticationRequest.getPhoneNumber())
                        .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
