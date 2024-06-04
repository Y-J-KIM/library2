package com.mysite.library.service;

import com.mysite.library.entity.UserEntity;
import com.mysite.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;


    //메소드를 완성하여 시큐리티가 유저이름으로 유저를 찾을수 있게 함.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("이메일 : " + email);
        Optional<UserEntity> _User = userRepo.findByEmail(email);
        if (_User.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        UserEntity user = _User.get(); //유저객체를 가져옴.
        System.out.println(user);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));


        return new User(user.getName(), user.getPassword(), authorities);
    }
}
