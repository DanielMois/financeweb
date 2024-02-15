package com.mois.financeweb.models;

import com.mois.financeweb.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    public void register(User user) {

        user.setCreateDate(new Date());
        user.setCurrentBalance(user.getFirstBalance());
        userRepository.save(user);
    }

    public User login(String login, String password) {
        User user = userRepository.findByLogin(login);
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

    public Long getCurrentUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
