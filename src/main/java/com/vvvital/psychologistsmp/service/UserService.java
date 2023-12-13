package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
import com.vvvital.psychologistsmp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final UserDTOMapper userDTOMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, CardRepository cardRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.cardRepository = cardRepository;
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

    public void deleteUser(String email) {
        User users = findByEmail(email);
        userRepository.delete(users);
    }

    public List<PsychologistResponseDTO> findAllPsych(Location location, Integer priceMin, Integer priceMax
            , Integer ratingMin, Integer ratingMax, Integer experienceMin, Integer experienceMax, Set<Categories> categories, String order) {
        logger.info("************* find All psychologists location={}  priceMin={} priceMax={} ratingMin={} ratingMax={} *************", location, priceMin, priceMax, ratingMin, ratingMax);
        List<User> psychologists = userRepository.findAllPsych();
        psychologists.forEach(System.out::println);
        if (location != Location.ALL) {
            psychologists = psychologists.stream()
                    .filter(psychologist -> psychologist.getLocation() == location)
                    .collect(Collectors.toList());
        }
        psychologists = selerctByCategories(psychologists, categories);
        psychologists = psychologists.stream()
                .filter(p -> p.getCard().getPrice() >= priceMin)
                .filter(p -> p.getCard().getPrice() <= priceMax)
                .filter(p -> p.getCard().getRating() >= ratingMin)
                .filter(p -> p.getCard().getRating() <= ratingMax)
                .filter(p -> p.getCard().getExperience() >= experienceMin)
                .filter(p -> p.getCard().getExperience() <= experienceMax)
                .collect(Collectors.toList());
        if (order != null && order.equals("price")) {
            psychologists.sort(Comparator.comparing(psychologist -> psychologist.getCard().getPrice()));
        } else if (order != null && order.equals("rating")) {
            psychologists.sort(Comparator.comparing(psychologist -> psychologist.getCard().getRating()));
        } else {
            psychologists.sort(Comparator.comparing(User::getId));
        }
        return psychologists.stream().map(PsychologistResponseDTO::toDTO).collect(Collectors.toList());
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

    public PsychologistCard updatePsychologistCard(Long id, PsychologistCardDTO cardDTO) {
        PsychologistCard existingPsychologistCard = cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingPsychologistCard.setPrice(cardDTO.getPrice());
        existingPsychologistCard.setRating(cardDTO.getRating());
        existingPsychologistCard.setExperience(cardDTO.getExperience());
        existingPsychologistCard.setSpecialization(cardDTO.getSpecialization());
        existingPsychologistCard.setDescription(cardDTO.getDescription());
        existingPsychologistCard.setPhotoLink(cardDTO.getPhotoLink());
        existingPsychologistCard.setCategories(cardDTO.getCategories());

        return cardRepository.save(existingPsychologistCard);
    }

    public PsychologistCard patchPsychologistCard(Long id, PsychologistCardDTO cardDTO) {
        PsychologistCard existingPsychologistCard = cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (cardDTO.getPrice() != null) {
            existingPsychologistCard.setPrice(cardDTO.getPrice());
        }
        if (cardDTO.getRating() != null) {
            existingPsychologistCard.setRating(cardDTO.getRating());
        }
        if (cardDTO.getExperience() != null) {
            existingPsychologistCard.setExperience(cardDTO.getExperience());
        }
        if (cardDTO.getSpecialization() != null) {
            existingPsychologistCard.setSpecialization(cardDTO.getSpecialization());
        }
        if (cardDTO.getDescription() != null) {
            existingPsychologistCard.setDescription(cardDTO.getDescription());
        }
        if (cardDTO.getPhotoLink() != null) {
            existingPsychologistCard.setPhotoLink(cardDTO.getPhotoLink());
        }
        if (cardDTO.getCategories() != null) {
            existingPsychologistCard.setCategories(cardDTO.getCategories());
        }

        return cardRepository.save(existingPsychologistCard);
    }
}