package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import com.example.ittalentsdominosproject.model.dto.UserRegistrationDTO;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public User login(String email, String password) {
        User u = userRepository.findByEmail(email);
        if(email == null || email.isBlank()){
            throw new BadRequestException("Username is mandatory");
        }
        if(password == null || password.isBlank() || !passwordEncoder.matches(password,u.getPassword())){
            throw new BadRequestException("Password is mandatory");
        }
        if(u == null){
            throw new UnauthorizedException("Wrong credentials");
        }
        return u;
    }

    public User register(UserRegistrationDTO u) {
        if(userRepository.findByEmail(u.getEmail())!=null){
            throw new BadRequestException("user with this email exists");
        }
        if(!u.getPassword().equals(u.getConfPassword())){
            throw new BadRequestException("Password mismatch");
        }
        User user = new User();
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail());
        user.setPhone(u.getPhone());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        return user;
    }
}
