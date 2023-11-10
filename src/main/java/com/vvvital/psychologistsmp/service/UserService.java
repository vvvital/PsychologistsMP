package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.PsychologistResponseDTO;
import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.dto.UserResponseDTO;
import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.Psychologist;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    Logger logger= LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
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

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<PsychologistResponseDTO> findAllPsych(Location location,Integer priceMin, Integer priceMax
            , Integer ratingMin, Integer ratingMax, Integer experienceMin, Integer experienceMax) {
        logger.info("************* find All psychologists location={}  priceMin={} priceMax={} ratingMin={} ratingMax={} *************",location,priceMin,priceMax,ratingMin,ratingMax);
        List<Psychologist> psychologists = userRepository.findAllPsych();
        psychologists=psychologists.stream()
                .filter(psychologist -> psychologist.getLocation()==location)
                .filter(psychologist -> psychologist.getCard().getPrice()>=priceMin)
                .filter(psychologist -> psychologist.getCard().getPrice()<=priceMax)
                .filter(psychologist -> psychologist.getCard().getRating()>=ratingMin)
                .filter(psychologist -> psychologist.getCard().getRating()<=ratingMax)
                .filter(psychologist -> psychologist.getCard().getExperience()>=experienceMin)
                .filter(psychologist -> psychologist.getCard().getExperience()<=experienceMax)
                .collect(Collectors.toList());
        List<PsychologistResponseDTO> dtoList = psychologists.stream().map(PsychologistResponseDTO::toDTO).collect(Collectors.toList());
        return dtoList;
    }
}