package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUser(String order) {
        return userRepository.findAll(Sort.by(order));
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete (User user){
        userRepository.delete(user);
    }
}
