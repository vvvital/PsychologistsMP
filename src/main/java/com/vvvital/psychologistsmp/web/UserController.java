package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<UserResponseDTO> save(@RequestBody User user, PsychologistCardDTO card) {
        logger.info("''''''''''''''''users/save\n{}\n{}\n{}\n{}''''''''''''''''''''''", user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
        UserResponseDTO responseDTO;
        User saveUser = null;
        if (user.getRole() == Role.PSYCHOLOGIST) {
            PsychologistCard saveCard = PsychologistCardDTO.toModel(card);
            Psychologist psychologist = PsychologistMapper.userToPsychologist(user, saveCard);
            saveUser = userService.save(psychologist);
            responseDTO = userDTOMapper.userToUserResponseDTO(saveUser);
        } else {
            saveUser = userService.save(user);
            responseDTO = userDTOMapper.userToUserResponseDTO(saveUser);
        }
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
    public ResponseEntity<List<PsychologistResponseDTO>> findAllPsychologist(
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
        Set<Categories> categoriesSet=null;
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

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO, PsychologistCardDTO card) {
        User updatedUser = userService.updateUser(id, userRequestDTO, card);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(updatedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<UserResponseDTO> patchUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO, PsychologistCardDTO card) {
        User patchedUser = userService.patchUser(id, userRequestDTO, card);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(patchedUser);
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

