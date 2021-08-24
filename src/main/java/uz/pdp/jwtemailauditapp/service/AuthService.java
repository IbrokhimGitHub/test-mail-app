package uz.pdp.jwtemailauditapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.jwtemailauditapp.enitity.Role;
import uz.pdp.jwtemailauditapp.enitity.User;
import uz.pdp.jwtemailauditapp.enitity.enums.RoleName;
import uz.pdp.jwtemailauditapp.payload.ApiResponse;
import uz.pdp.jwtemailauditapp.payload.RegisterDto;
import uz.pdp.jwtemailauditapp.repository.RoleRepository;
import uz.pdp.jwtemailauditapp.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Such email already exist", false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER)));

        user.setEmailCode(UUID.randomUUID().toString());




        Boolean aBoolean = sendEmail(user.getEmail(), user.getEmailCode());
        if (aBoolean) {
            userRepository.save(user);
            return new ApiResponse("verify your email please", true);
        }else {
            return new ApiResponse("something went wrong", false);
        }



    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("i.irmukhamedov@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("verify your email");
            simpleMailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlash linki</a>");
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account verified", true);
        }
        return new ApiResponse("Account already verified", false);

    }
}
