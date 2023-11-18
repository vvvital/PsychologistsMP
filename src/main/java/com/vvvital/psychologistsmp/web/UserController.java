package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.apache.bcel.classfile.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserService userService, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/save")
    @Operation(summary = "Save user")
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO user) {
        logger.info("''''''''''''''''users/save\n{}\n{}\n{}\n{}\n{}''''''''''''''''''''''", user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),user.getRoles());
        User saveUser = userService.save(userDTOMapper.requestDTOToUser(user));
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(saveUser);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/save/{id}/psychologist/")
    @Operation(summary = "To become a psychologist")
    public ResponseEntity<UserResponseDTO>  becomePsychologist(@PathVariable Long id,
                                                               @RequestBody UserRequestDTO userRequestDTO, PsychologistCardDTO card) {
        User becomePsychologist = userService.becomePsychologist(id, userRequestDTO, card);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(becomePsychologist);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {
        logger.info("********* find by email {}", email);
        User user = userService.findByEmail(email);
        if (user != null) {
            UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(user);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDTO>> findAllUsersResponseDTO() {
        logger.info("******************* users/all ****************");
        List<UserResponseDTO> responseDTOs = userService.findAll();
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/all/psychologist")
    @Operation(summary = "Get all psychologists")
    public ResponseEntity<List<UserResponseDTO>> findAllPsychologist(
            @RequestParam(required = false, defaultValue = "ALL") String location,
            @RequestParam(required = false, defaultValue = "0") String priceMin,
            @RequestParam(required = false, defaultValue = "99999") String priceMax,
            @RequestParam(required = false, defaultValue = "0") String ratingMin,
            @RequestParam(required = false, defaultValue = "5") String ratingMax,
            @RequestParam(required = false, defaultValue = "0") String experienceMin,
            @RequestParam(required = false, defaultValue = "99") String experienceMax,
            @RequestParam(required = false) String[] categories,
            @RequestParam(required = false) String order
    ) {
        logger.info("************* find All psychologists priceMin={} priceMax={} ratingMin={} ratingMax={} *************", priceMin, priceMax, ratingMin, ratingMax);
        Set<Categories> categoriesSet = null;
        if (categories != null) {
            categoriesSet = Arrays.stream(categories).map(Categories::valueOf).collect(Collectors.toSet());
        }
        return ResponseEntity.ok(userService.findAllPsych(Location.valueOf(location), strToInt(priceMin), strToInt(priceMax)
                , strToInt(ratingMin), strToInt(ratingMax), strToInt(experienceMin), strToInt(experienceMax), categoriesSet, order));
    }

    @DeleteMapping("/delete/{email}")
    @Operation(summary = "Delete user")
    public ResponseEntity<User> delete(@PathVariable String email) {
        logger.info("********* find by email {}", email);
        User user = userService.findByEmail(email);
        userService.deleteUser(email);
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}/general-information")
    @Operation(summary = "Update general information")
    public ResponseEntity<UserResponseDTO> updateGeneralInformation(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        User updatedUser = userService.updateGeneralInformation(id, userRequestDTO);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(updatedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/general-information")
    @Operation(summary = "Update general information")
    public ResponseEntity<UserResponseDTO> patchGeneralInformation(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        User patchedUser = userService.patchGeneralInformation(id, userRequestDTO);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(patchedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card")
    public ResponseEntity<PsychologistCardDTO> updatePsychologistCard(@PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO) {
        PsychologistCard updatePsychologistCard = userService.updatePsychologistCard(id, cardDTO);
        PsychologistCardDTO responseDTO = PsychologistCardDTO.toDTO(updatePsychologistCard);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card")
    public ResponseEntity<PsychologistCardDTO> patchPsychologistCard(@PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO) {
        PsychologistCard patchedPsychologistCard = userService.patchPsychologistCard(id, cardDTO);
        PsychologistCardDTO responseDTO = PsychologistCardDTO.toDTO(patchedPsychologistCard);
        return ResponseEntity.ok(responseDTO);
    }

    public Integer strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}

