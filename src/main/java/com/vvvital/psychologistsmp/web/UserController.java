package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.UpdateUserDTO;
import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.dto.UserRequestDTO;
import com.vvvital.psychologistsmp.dto.UserResponseDTO;
import com.vvvital.psychologistsmp.model.Categories;
import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<?> updateGeneralInformation(@Parameter(description = "User's email") @PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO, Principal principal) {
        try {
            User updatedUser = userService.updateGeneralInformation(email, updateUserDTO, principal);
            UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(updatedUser);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{email}/general-information")
    @Operation(summary = "Update general information",
            description = "Update SOME variables a general user information by specifying it's id user. The response is user with id, email, first name, last name, location and role.")
    public ResponseEntity<?> patchGeneralInformation(@Parameter(description = "User's email") @PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO, Principal principal) {
        try {
            User patchedUser = userService.patchGeneralInformation(email, updateUserDTO, principal);
            UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(patchedUser);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/{userId}/profileImage")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId,
                                                     @RequestParam("file") MultipartFile file) {
        try {
            userService.uploadProfileImage(userId, file);
            return ResponseEntity.ok("Profile image uploaded successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload profile image: " + e.getMessage());
        }
    }


    @GetMapping("/locations")
    @Operation(summary = "Get list of all locations")
    public List<String> getLocation(){
        return Arrays.stream(Location.values()).toList().stream().map(Location::name).sorted().collect(Collectors.toList());
    }

    @GetMapping("/categories")
    @Operation(summary = "Get list of all categories")
    public List<Categories> getCategories(){
        return Arrays.stream(Categories.values()).sorted().collect(Collectors.toList());
    }

}

