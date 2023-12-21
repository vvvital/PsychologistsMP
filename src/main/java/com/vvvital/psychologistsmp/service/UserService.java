package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
import com.vvvital.psychologistsmp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public User save(User user) {
        System.out.println("User service.save " + user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userDTOMapper::userToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(String email, Principal principal) throws Exception {
        if (principal.getName().equals(email)) {
            User users = findByEmail(email);
            userRepository.delete(users);
        } else {
            throw new IllegalStateException(" Violation of access rights");
        }
    }


    public User updateGeneralInformation(String email, UserRequestDTO userRequestDTO, Principal principal) throws Exception {
        if (principal.getName().equals(email)) {
            User existingGeneralInformation = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

            existingGeneralInformation.setEmail(userRequestDTO.getEmail());
            existingGeneralInformation.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            existingGeneralInformation.setFirstName(userRequestDTO.getFirstName());
            existingGeneralInformation.setLastName(userRequestDTO.getLastName());
            existingGeneralInformation.setRoles(userRequestDTO.getRoles());
            existingGeneralInformation.setLocation(userRequestDTO.getLocation());

            return userRepository.save(existingGeneralInformation);
        } else {
            throw new IllegalStateException(" Violation of access rights");
        }
    }

    public User patchGeneralInformation(String email, UserRequestDTO userRequestDTO, Principal principal) throws Exception {
        if (principal.getName().equals(email)) {
            User existingGeneralInformation = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

            if (userRequestDTO.getEmail() != null) {
                existingGeneralInformation.setEmail(userRequestDTO.getEmail());
            }
            if (userRequestDTO.getPassword() != null) {
                existingGeneralInformation.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            }
            if (userRequestDTO.getFirstName() != null) {
                existingGeneralInformation.setFirstName(userRequestDTO.getFirstName());
            }
            if (userRequestDTO.getLastName() != null) {
                existingGeneralInformation.setLastName(userRequestDTO.getLastName());
            }
            if (userRequestDTO.getRoles() != null) {
                existingGeneralInformation.setRoles(userRequestDTO.getRoles());
            }
            if (userRequestDTO.getLocation() != null) {
                existingGeneralInformation.setLocation(userRequestDTO.getLocation());
            }

            return userRepository.save(existingGeneralInformation);
        } else {
            throw new IllegalStateException(" Violation of access rights");
        }
    }
}