package com.mysite.library.service;


import com.mysite.library.dto.UserDTO;
import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.UserRepository;
import com.mysite.library.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // repository의 save 메소드 호출해야 한다 (조건: entity 객체를 넘겨줘야한다)
        // 1. dto -> entity 변환
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        // 2. repository의 save 메소드 호출
        // repository에서 쓰이는 save 메소드는 jp가 제공해주는 메소드이다. 그래서 여기서 'save' 메소드 이름은 꼭 지켜줘야한다
        userRepository.save(userEntity);
    }

//    public UserDTO login(UserDTO userDTO) {
//        // 1. 회원이 입력한 이메일을 DB에서 조회함
//        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
//        Optional<UserEntity> byUserEmail = userRepository.findByEmail(userDTO.getEmail());
//        if (byUserEmail.isPresent()) {
//            // 조회 결과가 있다 (= 해당 이메일이 DB에 존재한다)
//
//            // Optional로 감싸진 객체를 벗겨내는 작업 -> 안의 객체를 가져올수 있음
//            UserEntity userEntity = byUserEmail.get();
//            // 벗겨낸 객체로 비밀번호 일치 여부를 확인하기
//            if (userEntity.getPassword().equals(userDTO.getPassword())) {
//                // 비밀번호가 일치한다 -> 로그인이 가능하다
//                // entity -> dto 변환 후 리턴하자
//                UserDTO dto = UserDTO.toUserDTO(userEntity);
//                return dto;
//            } else {
//                // 비밀번호가 불일치한다
//                return null;
//            }
//        } else {
//            // 조회 결과가 없다 (= 해당 이메일이 DB에 없다)
//            return null;
//        }
//
//
//    }


//    public UserEntity findByEmail(String email) {
//
//        return userRepository.findByEmail(email).orElse(null);
//    }

    public UserEntity findByName(String name) {

        return userRepository.findByName(name).orElse(null);
    }



    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findByRole(UserRole.USER).stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setIdx(user.getIdx());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setQuestionCount(user.getQuestions().size());
            return dto;
        }).collect(Collectors.toList());
    }


//    public UserDTO findById(Long idx) {
//        return UserDTO.toUserDTO(userRepository.findById(idx).orElse(null));
//    }
}
