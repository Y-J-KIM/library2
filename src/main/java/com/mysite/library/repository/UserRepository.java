package com.mysite.library.repository;

import com.mysite.library.entity.UserEntity;
import com.mysite.library.UserRole;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


// Jparepoxitory<어떤 entity 클래스를 받을 것인지, 그 클래스의 pk의 타입>
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 회원 정보 조회가 가능하다 (=> select * from user where email=?)
    // Optional: null을 방지한다
    // findBy{조회하고자하는 필드 이름}을 적는다. 대소문자는 구분하지 않음.
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByName(String name);

    List<UserEntity> findByRole(UserRole role);
}
