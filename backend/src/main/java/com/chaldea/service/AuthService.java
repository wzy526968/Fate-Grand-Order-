package com.chaldea.service;

import com.chaldea.dto.LoginRequest;
import com.chaldea.dto.LoginResponse;
import com.chaldea.exception.AuthException;
import com.chaldea.model.User;
import com.chaldea.repository.UserRepository;
import com.chaldea.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthService(UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public LoginResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("User not found"));
        return new LoginResponse(null, user.getUsername(),
                user.getDisplayName() != null ? user.getDisplayName() : user.getUsername(),
                user.getRole());
    }

    public LoginResponse login(LoginRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                throw new AuthException("Invalid username or password");
            }

            String token = jwtTokenProvider.generateToken(userDetails);

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AuthException("User not found"));

            return new LoginResponse(token, user.getUsername(),
                    user.getDisplayName() != null ? user.getDisplayName() : user.getUsername(),
                    user.getRole());

        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException("Invalid username or password");
        }
    }
}
