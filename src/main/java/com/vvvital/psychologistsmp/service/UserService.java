package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.exception.BadRequestException;
import com.vvvital.psychologistsmp.exception.GCPFileUploadException;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.UserRepository;
import com.vvvital.psychologistsmp.util.DataBucketUtil;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DataBucketUtil dataBucketUtil;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, BCryptPasswordEncoder passwordEncoder, DataBucketUtil dataBucketUtil) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.dataBucketUtil = dataBucketUtil;
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
    public void uploadProfileImage(Long id, MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id: " + id));

        logger.debug("Start file uploading service");

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new BadRequestException("Original file name is null");
        }
        Path path = new File(originalFileName).toPath();

        try {
            String contentType = Files.probeContentType(path);
            String fileUrl = dataBucketUtil.uploadFile(file, originalFileName, contentType);

            user.setPhotoLink(fileUrl);
            userRepository.save(user);

            logger.debug("File uploaded successfully, url: {}", fileUrl);

        } catch (Exception e) {
            logger.error("Error occurred while uploading. Error: ", e);
            throw new GCPFileUploadException("Error occurred while uploading");
        }
    }
}