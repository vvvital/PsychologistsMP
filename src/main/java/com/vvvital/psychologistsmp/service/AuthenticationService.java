package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.JwtAuthenticationResponse;
import com.vvvital.psychologistsmp.dto.LoginRequest;
import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.dto.UserRequestDTO;
import com.vvvital.psychologistsmp.model.User;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthenticationService {

    private final SecurityUserService securityUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper dtoMapper;

    public AuthenticationService(SecurityUserService securityUserService, AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder, UserDTOMapper dtoMapper) {
        this.securityUserService = securityUserService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.dtoMapper = dtoMapper;
    }

    public JwtAuthenticationResponse signUp(UserRequestDTO request) {
        User user=dtoMapper.requestDTOToUser(request);
        userService.save(user);
        UserDetails userDetails = securityUserService.loadUserByUsername(user.getEmail());
        String jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = securityUserService.loadUserByUsername(request.getEmail());
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
