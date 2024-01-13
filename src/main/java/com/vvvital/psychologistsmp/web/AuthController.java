package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.JwtAuthenticationResponse;
import com.vvvital.psychologistsmp.dto.LoginRequest;
import com.vvvital.psychologistsmp.dto.UserRequestDTO;
import com.vvvital.psychologistsmp.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody LoginRequest request){
        return authenticationService.signIn(request);
    }

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody UserRequestDTO request){
        return authenticationService.signUp(request);
    }
}
