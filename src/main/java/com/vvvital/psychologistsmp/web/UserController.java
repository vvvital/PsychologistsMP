package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.dto.UserRequestDTO;
import com.vvvital.psychologistsmp.dto.UserResponseDTO;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {
    private final Logger logger= LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserService userService, UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/save")
    @Operation(summary = "Save user")
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestDTO userRequestDTO) {
        logger.info("''''''''''''''''users/save''''''''''''''''''''''");
        User user = userDTOMapper.requestDTOToUser(userRequestDTO);
        User savedUser = userService.save(user);
        UserResponseDTO responseDTO = userDTOMapper.userToUserResponseDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {
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

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user")
    public ResponseEntity<String> delete(@RequestBody User user) {
        userService.delete(user);
        return ResponseEntity.ok("User deleted successfully");
    }
}