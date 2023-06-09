package de.thb.MACJEE.Controller;

import de.thb.MACJEE.Entitys.Role;
import de.thb.MACJEE.Entitys.UserEntity;
import de.thb.MACJEE.Repository.RoleRepository;
import de.thb.MACJEE.Repository.UserRepository;
import de.thb.MACJEE.Security.JWTGenerator;
import de.thb.MACJEE.Dto.LoginDto;
import de.thb.MACJEE.Dto.RegisterDto;
import de.thb.MACJEE.Service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/MACJEE/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JWTGenerator jwtGenerator;

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginTest"; // Assuming you have a login.html Thymeleaf template
    }

    @PostMapping("/login")
    public String login(LoginDto loginDto, Model model) {
        try {
            UserDetails user = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            user.getUsername(), loginDto.getPassword(), user.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //String token = jwtGenerator.generateToken(authentication);
            //model.addAttribute("token", token);
            return "redirect:/dashboard";
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid username or password");
            return "loginTest"; // Return back to the login page with an error message
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "registerTest";
    }

    @PostMapping("/register")
    public String register(RegisterDto registerDto, Model model) {
        if (customUserDetailsService.existsByUsername(registerDto.getUsername())) {
            model.addAttribute("error", "Username is taken!");
            return "registerTest"; // Return back to the register page with an error message
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
            return "loginTest"; // Return back to the login page with a success message
        } else {
            model.addAttribute("error", "USER role not found!");
            return "registerTest"; // Return back to the register page with an error message
        }
    }

}
