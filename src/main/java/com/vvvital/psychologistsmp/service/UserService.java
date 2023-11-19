package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
import com.vvvital.psychologistsmp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.cardRepository = cardRepository;
    }


    public User save(User user) {
        System.out.println("User service.save " + user.getRoles());
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

    public void deleteUser(String email) {
        User users = findByEmail(email);
        userRepository.delete(users);
    }

    public List<UserResponseDTO> findAllPsych(Location location, Integer priceMin, Integer priceMax
            , Integer ratingMin, Integer ratingMax, Integer experienceMin, Integer experienceMax, Set<Categories> categories, String order) {
        logger.info("************* find All psychologists location={}  priceMin={} priceMax={} ratingMin={} ratingMax={} *************", location, priceMin, priceMax, ratingMin, ratingMax);
        List<User> psychologists = userRepository.findAllPsych();
        if (location != Location.ALL) {
            psychologists = psychologists.stream()
                    .filter(psychologist -> psychologist.getLocation() == location)
                    .collect(Collectors.toList());
        }
        psychologists = selerctByCategories(psychologists, categories);
        psychologists.forEach(p-> System.out.println(p.toString()));
        psychologists = psychologists.stream()
                .filter(psychologist -> psychologist.getCard().getPrice() >= priceMin)
                .filter(psychologist -> psychologist.getCard().getPrice() <= priceMax)
                .filter(psychologist -> psychologist.getCard().getRating() >= ratingMin)
                .filter(psychologist -> psychologist.getCard().getRating() <= ratingMax)
                .filter(psychologist -> psychologist.getCard().getExperience() >= experienceMin)
                .filter(psychologist -> psychologist.getCard().getExperience() <= experienceMax)
                .collect(Collectors.toList());
        psychologists.forEach(p-> System.out.println(p.toString()));
        if (order != null && order.equals("price")) {
            psychologists.sort(Comparator.comparing(psychologist -> psychologist.getCard().getPrice()));
        } else if (order != null && order.equals("rating")) {
            psychologists.sort(Comparator.comparing(psychologist -> psychologist.getCard().getRating()));
        } else {
            psychologists.sort(Comparator.comparing(User::getId));
        }
        return psychologists.stream().map(userDTOMapper::userToUserResponseDTO).collect(Collectors.toList());
    }

    public List<User> selerctByCategories(List<User> psychologists, Set<Categories> categories) {
        List<User> psychologistList = new ArrayList<>();
        if (categories != null && !categories.isEmpty()) {
            for (User p : psychologists
            ) {
                if (categories.stream().anyMatch(cat1 -> p.getCard().getCategories().stream().anyMatch(cat1::equals))) {
                    psychologistList.add(p);
                }
            }
            return psychologistList;
        }
        return psychologists;
    }

    public User updateGeneralInformation(Long id, UserRequestDTO userRequestDTO) {
        User existingGeneralInformation = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingGeneralInformation.setEmail(userRequestDTO.getEmail());
        existingGeneralInformation.setPassword(userRequestDTO.getPassword());
        existingGeneralInformation.setFirstName(userRequestDTO.getFirstName());
        existingGeneralInformation.setLastName(userRequestDTO.getLastName());
        existingGeneralInformation.setRoles(userRequestDTO.getRoles());
        existingGeneralInformation.setLocation(userRequestDTO.getLocation());

        return userRepository.save(existingGeneralInformation);
    }

    public User patchGeneralInformation(Long id, UserRequestDTO userRequestDTO) {
        User existingGeneralInformation = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (userRequestDTO.getEmail() != null) {
            existingGeneralInformation.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null) {
            existingGeneralInformation.setPassword(userRequestDTO.getPassword());
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
    }

    public User becomePsychologist(Long id, PsychologistCardDTO card) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        currentUser.setRoles(Role.PSYCHOLOGIST);
//        if (userRequestDTO.getRoles() != null) {
//            currentUser.setRoles(userRequestDTO.getRoles());
//        }
        if (currentUser.getCard() != null) {
            throw new IllegalStateException("User already has a psychologist card");
        } else {
            PsychologistCard psychologistCard = PsychologistCardDTO.toModel(card);
            currentUser.setCard(psychologistCard);
        }
        return userRepository.save(currentUser);
    }
}