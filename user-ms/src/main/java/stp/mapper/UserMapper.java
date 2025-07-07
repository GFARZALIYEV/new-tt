package stp.mapper;

import org.mapstruct.Mapper;
import stp.dto.userdto.UserRegistrationDto;
import stp.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User regDtoToUser(UserRegistrationDto user);



}
