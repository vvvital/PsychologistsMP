package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.dto.UserResponseDTO;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserDTOMapper userDTOMapper;


    public User save(User user) {
        return userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public List<UserResponseDTO> findAll() {
        return null;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
