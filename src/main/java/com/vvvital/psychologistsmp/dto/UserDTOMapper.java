package com.vvvital.psychologistsmp.dto;

import com.vvvital.psychologistsmp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    UserResponseDTO userToUserResponseDTO(User user);

    User responseDTOToUser(UserResponseDTO dto);

    UserRequestDTO userToUserRequestDTO(User user);

    User requestDTOToUser(UserRequestDTO dto);

    List<UserResponseDTO> usersToUserResponseDTO(List<User> users);

    List<PsychologistResponseDTO> userToPsychologistResponseDTO(List<User> users);

}
