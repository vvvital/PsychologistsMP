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
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        System.out.println("user save "+ user.getEmail());
        UserDetails userDetails = securityUserService.loadUserByUsername(user.getEmail());
        System.out.println("userDetails load user "+userDetails);
        String jwt = jwtService.generateToken(userDetails);
        System.out.println("token=" +jwt);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(LoginRequest request) {
        System.out.println("authenticationManager" + request.getEmail()+request.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = securityUserService.loadUserByUsername(request.getEmail());
        System.out.println("userdetails "+ user.getUsername());
        String jwt = jwtService.generateToken(user);
        System.out.println("generate token" + jwt);
        return new JwtAuthenticationResponse(jwt);
    }

}
