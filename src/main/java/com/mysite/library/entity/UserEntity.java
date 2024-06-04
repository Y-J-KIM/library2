package com.mysite.library.entity;

import com.mysite.library.dto.UserDTO;
import com.mysite.library.UserRole;
import jakarta.persistence.*;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
@AllArgsConstructor
// Entity 클래스가 일종의 테이블과 같은 역할을 한다
public class UserEntity {
    // @Id는 프라이머리 키를 지정할때 쓴다
    @Id
    // auto_increment를 지정한다
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String name; //한글 이름

    @Column(unique = true)
    // @Column은 일반 컬럼 설정을 의미하는데 유니크 제약 설정을 추가할수 있다
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Question> questions;





    public static UserEntity toUserEntity(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();


        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setRole(userDTO.getRole() != null ? userDTO.getRole() : UserRole.USER);
        return userEntity;

    }
}




