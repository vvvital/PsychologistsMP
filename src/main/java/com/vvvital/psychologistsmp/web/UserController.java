package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.*;
import com.vvvital.psychologistsmp.model.*;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Utilities;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Operation(summary = "Save user",
            description = "Save user with general information. The response is user with id, email, first name, last name, location and role.")
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO user) {
        logger.info("''''''''''''''''users/save\n{}\n{}\n{}\n{}\n{}''''''''''''''''''''''", user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRoles());
        User saveUser = userService.save(userDTOMapper.requestDTOToUser(user));
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(saveUser);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/save/{id}/psychologist/")
    @Operation(summary = "To become a psychologist",
            description = "User becomes a psychologist by id and add a psychologist card.")
    public ResponseEntity<UserResponseDTO> becomePsychologist(@Parameter(description = "User's id") @PathVariable Long id,
                                                              @RequestBody PsychologistCardDTO card) {
        User becomePsychologist = userService.becomePsychologist(id, card);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(becomePsychologist);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserResponseDTO> findByEmail(@Parameter(description = "User's email") @PathVariable String email) {
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
        Set<Categories> categoriesSet = null;
        if (categories != null) {
            categoriesSet = Arrays.stream(categories).map(Categories::valueOf).collect(Collectors.toSet());
        }
        return ResponseEntity.ok(userService.findAllPsych(Location.valueOf(location), strToInt(priceMin), strToInt(priceMax)
                , strToInt(ratingMin), strToInt(ratingMax), strToInt(experienceMin), strToInt(experienceMax), categoriesSet, order));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PsychologistResponseDTO> get(@PathVariable Long id){
        User psychologist= userService.getById(id);
        if (psychologist!=null){
            return ResponseEntity.ok(userDTOMapper.userToPsychologistResponseDTO(List.of(psychologist)).get(0));
        }else {
            return null;
        }
    }

    @DeleteMapping("/delete/{email}")
    @Operation(summary = "Delete user")
    public ResponseEntity<?> delete(@Parameter(description = "User's email") @PathVariable String email, Principal principal) throws Exception {
        logger.info("********* find by email {}", email);
        User user = userService.findByEmail(email);
        try {
            userService.deleteUser(email, principal);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{email}/general-information")
    @Operation(summary = "Update general information",
            description = "Update ALL variables a general user information by specifying it's id user. The response is user with id, email, first name, last name, location and role.")
    public ResponseEntity<?> updateGeneralInformation(@Parameter(description = "User's email") @PathVariable String email, @RequestBody UserRequestDTO userRequestDTO, Principal principal) {
        try {
            User updatedUser = userService.updateGeneralInformation(email, userRequestDTO, principal);
            UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(updatedUser);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/general-information")
    @Operation(summary = "Update general information",
            description = "Update SOME variables a general user information by specifying it's id user. The response is user with id, email, first name, last name, location and role.")
    public ResponseEntity<?> patchGeneralInformation(@Parameter(description = "User's email") @PathVariable String email, @RequestBody UserRequestDTO userRequestDTO, Principal principal) {
        try {
            User patchedUser = userService.patchGeneralInformation(email, userRequestDTO, principal);
            UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(patchedUser);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card",
            description = "Update ALL variables a psychologist card by specifying it's id. The response is psychologist card with price, rating, experience, description, photoLink and categories."
    )
    public ResponseEntity<?> updatePsychologistCard(@Parameter(description = "Psychologist's card id") @PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO, Principal principal) {
        try {
            PsychologistCard updatePsychologistCard = userService.updatePsychologistCard(id, cardDTO, principal);
            PsychologistCardDTO responseDTO = PsychologistCardDTO.toDTO(updatePsychologistCard);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card",
            description = "Update SOME variables a psychologist card by specifying it's id. The response is psychologist card with price, rating, experience, description, photoLink and categories.")
    public ResponseEntity<?> patchPsychologistCard(@Parameter(description = "Psychologist's card id") @PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO, Principal principal) {
        try {
            PsychologistCard patchedPsychologistCard = userService.patchPsychologistCard(id, cardDTO, principal);
            PsychologistCardDTO responseDTO = PsychologistCardDTO.toDTO(patchedPsychologistCard);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Integer strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}

