package com.baobao.controller;


import com.baobao.model.User;
import com.baobao.repository.UserRepository;
import com.baobao.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest tLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tLogin.getLogin(), tLogin.getSenha()));
        return jwtUtil.generateToken(tLogin.getLogin(), tLogin.getSenha());
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setSenha(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(user.getSenha()));
        userRepository.save(user);
        return "User registered!";
    }

    public static class LoginRequest {
        private String login;
        private String senha;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
