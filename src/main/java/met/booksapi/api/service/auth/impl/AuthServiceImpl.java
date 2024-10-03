package met.booksapi.api.service.auth.impl;

import lombok.RequiredArgsConstructor;
import met.booksapi.api.dto.AuthResponseDTO;
import met.booksapi.api.dto.LoginRequestDTO;
import met.booksapi.api.dto.RegisterRequestDTO;
import met.booksapi.api.entity.user.Role;
import met.booksapi.api.entity.user.User;
import met.booksapi.api.repository.user.UserRepository;
import met.booksapi.api.service.auth.AuthService;
import met.booksapi.jwt.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponseDTO.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
