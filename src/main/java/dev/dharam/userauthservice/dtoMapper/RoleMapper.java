package dev.dharam.userauthservice.dtoMapper;

import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.entity.Role;

public class RoleMapper {
    public static CreateRoleResponseDto convertRoleToCreateRoleResponseDto(Role role){
        CreateRoleResponseDto responseDto = new CreateRoleResponseDto();
        responseDto.setName(role.getName());
        return responseDto;
    }


}
