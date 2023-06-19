package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Entitys.UserEntity;
import de.thb.MACJEE.Repository.RoleRepository;
import de.thb.MACJEE.Dto.RegisterDto;
import de.thb.MACJEE.Service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/registerTest";
    }

    @PostMapping("/register")
    public String register(RegisterDto registerDto, Model model) {
        if (customUserDetailsService.existsByUsername(registerDto.getUsername())) {
            model.addAttribute("error", "Username is taken!");
            return "auth/registerTest"; // Return back to the register page with an error message
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            user.setRoles(Collections.singletonList(role));

            customUserDetailsService.save(user);

            model.addAttribute("success", "User registered successfully!");
            return "auth/loginTest"; // Return back to the login page with a success message
        } else {
            model.addAttribute("error", "USER role not found!");
            return "auth/registerTest"; // Return back to the register page with an error message
        }
    }

}
