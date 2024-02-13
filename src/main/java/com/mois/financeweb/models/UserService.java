package com.mois.financeweb.models;

import com.mois.financeweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        userRepository.save(user);
    }

    public User login(String name, String password) {
        User user = userRepository.findByName(name);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void updateSalary(Long userId, BigDecimal salary) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setSalary(salary);
            userRepository.save(user);
        }
    }
}
