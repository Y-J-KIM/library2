package com.mysite.library.dto;

import com.mysite.library.entity.UserEntity;
import com.mysite.library.UserRole;
import lombok.*;


@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long idx;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private UserRole role;
    private int questionCount;


    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setIdx(userEntity.getIdx());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setAddress(userEntity.getAddress());
        userDTO.setRole(userEntity.getRole());

        return userDTO;
    }

}
