package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(Location location, Integer price, Integer rating, Integer experience, Set<Categories> categories) {
        List<User> users=userRepository.findAll();
        return users.stream().filter(user -> user.getLocation().equals(location)).collect(Collectors.toList());
    }

    public void delete(User user){
        userRepository.delete(user);
    }
}
