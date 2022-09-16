package com.clearsolutions.registeruser;

import com.clearsolutions.registeruser.dto.UserDTO;
import com.clearsolutions.registeruser.model.UserModel;
import com.clearsolutions.registeruser.repository.UserRepository;
import com.clearsolutions.registeruser.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
final class RegisterUserApplicationTests {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ModelMapper mapper;

	@BeforeEach
	public void setup() {
		this.mapper = new ModelMapper();
	}

	@Test
	public void whenMapGameWithExactMatch_thenConvertsToDTO() {
		UserModel userModel = new UserModel("1", "helen@.gmail.com", "Helen",
				"Hrebeniuk", LocalDate.parse("2001-07-26"), "Sechenova 7",
				"567-56-18");
		UserDTO userDTO = this.mapper.map(userModel, UserDTO.class);

		assertEquals(userModel.getUserId(), userDTO.getUserId());
		assertEquals(userModel.getEmail(), userDTO.getEmail());
	}

	@Test
	public void getUserByBirthdateRangeTest(){
		List<UserDTO> result = userService.getUsersByBirthDateRange(LocalDate.parse("1996-08-30"), LocalDate.parse("1998-08-30"));

		for(UserDTO user : result){
			assertEquals("1", user.getBirthDate());
		}
		assertNotNull(result);
	}

	@Test
	public void deleteUserByIdTest() {
		UserModel userModel = new UserModel("1", "helen@.gmail.com", "Helen",
				"Hrebeniuk", LocalDate.parse("2001-07-26"), "Sechenova 7",
				"567-56-18");

		userService.deleteUser(userModel.getUserId());
		assertThat(userRepository.count()).isEqualTo(1);
	}

	@Test
	void createUserTest() {

		UserModel userModel = new UserModel("1", "helen@.gmail.com", "Helen",
				"Hrebeniuk", LocalDate.parse("2001-07-26"), "Sechenova 7",
				"567-56-18");
		UserDTO userDTO = this.mapper.map(userModel, UserDTO.class);

		userService.createUser(userDTO);
		Mockito.verify(userService, times((1))).createUser(userDTO);
	}


}
