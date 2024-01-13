package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.JwtAuthenticationResponse;
import com.vvvital.psychologistsmp.dto.LoginRequest;
import com.vvvital.psychologistsmp.dto.UserRequestDTO;
import com.vvvital.psychologistsmp.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
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
