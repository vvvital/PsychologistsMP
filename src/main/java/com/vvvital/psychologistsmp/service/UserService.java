package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
import com.vvvital.psychologistsmp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
        List<Psychologist> psychologists = userRepository.findAllPsych();
        if (location != Location.ALL) {
            psychologists = psychologists.stream()
                    .filter(psychologist -> psychologist.getLocation() == location)
                    .collect(Collectors.toList());
        }
        psychologists = selerctByCategories(psychologists, categories);
        psychologists = psychologists.stream()
                .filter(psychologist -> psychologist.getCard().getPrice() >= priceMin)
                .filter(psychologist -> psychologist.getCard().getPrice() <= priceMax)
                .filter(psychologist -> psychologist.getCard().getRating() >= ratingMin)
                .filter(psychologist -> psychologist.getCard().getRating() <= ratingMax)
                .filter(psychologist -> psychologist.getCard().getExperience() >= experienceMin)
                .filter(psychologist -> psychologist.getCard().getExperience() <= experienceMax)
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

    public List<Psychologist> selerctByCategories(List<Psychologist> psychologists, Set<Categories> categories) {
        List<Psychologist> psychologistList = new ArrayList<>();
        if (categories != null && !categories.isEmpty()) {
            for (Psychologist p : psychologists
            ) {
                if (categories.stream().anyMatch(cat1 -> p.getCard().getCategories().stream().anyMatch(cat1::equals))) {
                    psychologistList.add(p);
                }
            }
            return psychologistList;
        }
        return psychologists;
    }

    public User updateUser(Long id, UserRequestDTO userRequestDTO, PsychologistCardDTO cardDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());
        existingUser.setFirstName(userRequestDTO.getFirstName());
        existingUser.setLastName(userRequestDTO.getLastName());
        existingUser.setRole(userRequestDTO.getRole());
        existingUser.setLocation(userRequestDTO.getLocation());

        if (existingUser.getRole() == Role.PSYCHOLOGIST && cardDTO != null) {
            PsychologistCard psychologistCard = ((Psychologist) existingUser).getCard();

            if (psychologistCard != null) {
                psychologistCard.setPrice(cardDTO.getPrice());
                psychologistCard.setRating(cardDTO.getRating());
                psychologistCard.setExperience(cardDTO.getExperience());
                psychologistCard.setDescription(cardDTO.getDescription());
                psychologistCard.setPhotoLink(cardDTO.getPhotoLink());
                psychologistCard.setCategories(cardDTO.getCategories());
            } else {
                psychologistCard = PsychologistCardDTO.toModel(cardDTO);
                ((Psychologist) existingUser).setCard(psychologistCard);
            }
        }

        return userRepository.save(existingUser);
    }

    public User patchUser(Long id, UserRequestDTO userRequestDTO, PsychologistCardDTO cardDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (userRequestDTO.getEmail() != null) {
            existingUser.setEmail(userRequestDTO.getEmail());
        }
        if (userRequestDTO.getPassword() != null) {
            existingUser.setPassword(userRequestDTO.getPassword());
        }
        if (userRequestDTO.getFirstName() != null) {
            existingUser.setFirstName(userRequestDTO.getFirstName());
        }
        if (userRequestDTO.getLastName() != null) {
            existingUser.setLastName(userRequestDTO.getLastName());
        }
        if (userRequestDTO.getRole() != null) {
            existingUser.setRole(userRequestDTO.getRole());
        }
        if (userRequestDTO.getLocation() != null) {
            existingUser.setLocation(userRequestDTO.getLocation());
        }

        if (existingUser.getRole() == Role.PSYCHOLOGIST && cardDTO != null) {
            PsychologistCard psychologistCard = ((Psychologist) existingUser).getCard();

            if (psychologistCard != null) {
                if (cardDTO.getPrice() != null) {
                    psychologistCard.setPrice(cardDTO.getPrice());
                }
                if (cardDTO.getRating() != null) {
                    psychologistCard.setRating(cardDTO.getRating());
                }
                if (cardDTO.getExperience() != null) {
                    psychologistCard.setExperience(cardDTO.getExperience());
                }
                if (cardDTO.getDescription() != null) {
                    psychologistCard.setDescription(cardDTO.getDescription());
                }
                if (cardDTO.getPhotoLink() != null) {
                    psychologistCard.setPhotoLink(cardDTO.getPhotoLink());
                }
                if (cardDTO.getCategories() != null) {
                    psychologistCard.setCategories(cardDTO.getCategories());
                }
            } else {
                psychologistCard = PsychologistCardDTO.toModel(cardDTO);
                ((Psychologist) existingUser).setCard(psychologistCard);
            }
        }

        return userRepository.save(existingUser);
    }
}