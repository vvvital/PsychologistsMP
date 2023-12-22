package com.vvvital.psychologistsmp.service;

import com.vvvital.psychologistsmp.dto.PsychologistCardDTO;
import com.vvvital.psychologistsmp.dto.PsychologistResponseDTO;
import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.exception.BadRequestException;
import com.vvvital.psychologistsmp.exception.GCPFileUploadException;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.repository.CardRepository;
import com.vvvital.psychologistsmp.repository.UserRepository;
import com.vvvital.psychologistsmp.util.DataBucketUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PsychologistService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final UserDTOMapper userDTOMapper;

    private final DataBucketUtil dataBucketUtil;

    @Autowired
    public PsychologistService(UserRepository userRepository, UserDTOMapper userDTOMapper, CardRepository cardRepository, DataBucketUtil dataBucketUtil) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.cardRepository = cardRepository;
        this.dataBucketUtil = dataBucketUtil;
    }

    public PsychologistResponseDTO becomePsychologist(Long id, PsychologistCardDTO card, Principal principal) throws IllegalAccessException {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        if (!currentUser.getEmail().equals(principal.getName())){
            throw new IllegalAccessException(" Violation of access rights");
        }
        currentUser.setRole(Role.PSYCHOLOGIST);
        if (currentUser.getCard() != null) {
            throw new IllegalStateException("User already has a psychologist card");
        } else {
            PsychologistCard psychologistCard = PsychologistCardDTO.toModel(card);
            currentUser.setCard(psychologistCard);
        }
        return PsychologistResponseDTO.toDTO(userRepository.save(currentUser));
    }

    public PsychologistResponseDTO getById(Long id) throws UsernameNotFoundException {
        User psychologist = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Psychologist not found with id = " + id));
        if (!psychologist.getRoles().contains(Role.PSYCHOLOGIST)||psychologist.getCard()==null){
            throw new UsernameNotFoundException("This user isn't psychologist");
        }
        return PsychologistResponseDTO.toDTO(psychologist);
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
        psychologists = selectByCategories(psychologists, categories);
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


    public PsychologistCardDTO updatePsychologistCard(Long id, PsychologistCardDTO cardDTO, Principal principal) throws Exception {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getCard().getId() == null || user.getCard().getId() != id) {
            throw new IllegalStateException(" Violation of access rights");
        } else {
            PsychologistCard existingPsychologistCard = cardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

            existingPsychologistCard.setPrice(cardDTO.getPrice());
            existingPsychologistCard.setRating(cardDTO.getRating());
            existingPsychologistCard.setExperience(cardDTO.getExperience());
            existingPsychologistCard.setSpecialization(cardDTO.getSpecialization());
            existingPsychologistCard.setDescription(cardDTO.getDescription());
            existingPsychologistCard.setPhotoLink(cardDTO.getPhotoLink());
            existingPsychologistCard.setCategories(cardDTO.getCategories());

            return PsychologistCardDTO.toDTO(cardRepository.save(existingPsychologistCard));
        }
    }

    public PsychologistCardDTO patchPsychologistCard(Long id, PsychologistCardDTO cardDTO, Principal principal) throws Exception {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getCard().getId() == null || user.getCard().getId() != id) {
            throw new IllegalAccessException(" Violation of access rights");
        } else {
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

            return PsychologistCardDTO.toDTO(cardRepository.save(existingPsychologistCard));
        }
    }

    public void uploadProfileImage(Long id, MultipartFile file) {
        PsychologistCard psychologistCard = cardRepository.findById(id)
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

            psychologistCard.setPhotoLink(fileUrl);
            cardRepository.save(psychologistCard);

            logger.debug("File uploaded successfully, url: {}", fileUrl);

        } catch (Exception e) {
            logger.error("Error occurred while uploading. Error: ", e);
            throw new GCPFileUploadException("Error occurred while uploading");
        }
    }

    public List<User> selectByCategories(List<User> psychologists, Set<Categories> categories) {
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
}