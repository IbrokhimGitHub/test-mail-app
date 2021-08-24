package uz.pdp.jwtemailauditapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.x509.RDN;
import uz.pdp.jwtemailauditapp.payload.ApiResponse;
import uz.pdp.jwtemailauditapp.payload.RegisterDto;
import uz.pdp.jwtemailauditapp.service.AuthService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
    AuthService authService;
    @PostMapping("/register")
    public HttpEntity<?> addUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:400).body(apiResponse);
    }
    @GetMapping("/verifyemail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode,@RequestParam String email){
        ApiResponse apiResponse=authService.verifyEmail(email,emailCode);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

}

