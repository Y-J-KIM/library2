package com.mysite.library;

import com.mysite.library.repository.UserRepository;
import com.mysite.library.service.UserService;
import com.mysite.library.service.UserService_;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService_ userService_;
    @Autowired
    private UserService userService;

	@Test
	void contextLoads() {

		//userRepository.deleteById(8L);
		//userService_.deleteById(6L);

		userService.findByName("aa@ss");
	}

}
