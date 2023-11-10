package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.Psychologist;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import com.vvvital.psychologistsmp.model.Role;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.apache.bcel.classfile.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserResponseDTO responseDTO = null;
        User saveUser = null;
        if (user.getRole() == Role.PSYCHOLOGIST) {
            PsychologistCard saveCard = PsychologistCardDTO.toModel(card);
            //PsychologistCard saveCard=null;
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
    public ResponseEntity<List<PsychologistResponseDTO>>findAllPsychologist(){
        logger.info("************* find All psychologists *************");
        return ResponseEntity.ok(userService.findAllPsych());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user")
    public ResponseEntity<String> delete(@RequestBody User user) {
        userService.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }
}