package net.snatchTech.cacheSimple;

import net.snatchTech.cacheSimple.api.CarController;
import net.snatchTech.cacheSimple.dao.CarDao;
import net.snatchTech.cacheSimple.dao.FakeCarDao;
import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.service.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = {CarController.class, CarService.class, FakeCarDao.class})
class CarControllerTests {

	/*@Autowired
	private WebApplicationContext webApplicationContext;*/
	@Autowired
	private MockMvc mockMvc;
	private final String PREFIX_URL = "/api/v1/";

	/*@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}*/
	@AfterEach
	public void after() throws NoSuchFieldException, IllegalAccessException {
		Field dbField = FakeCarDao.class.getDeclaredField("DB");
		dbField.setAccessible(true);
		List<Car> db = (List<Car>) dbField.get(null);
		db.clear();
	}

	@Test
	public void getAllCarsFromEmptyDB() throws Exception {
		this.mockMvc.perform(get(PREFIX_URL + "car/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string("[]"));
	}

	@Test
	public void addCar() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(post(PREFIX_URL + "car/add")
				.param("number", "q123we"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andDo(print())
				//.andExpect(content().json("{\"id\":\"c71062e1-7418-450c-a24c-0cc42f31aa4b\",\"number\":\"q123we\",\"make\":null,\"model\":null,\"owner\":null}"))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertNotNull(content);
		//,"number":"q123we",
		assertTrue(content.contains(",\"number\":\"q123we\","));
	}

	@Test
	public void getCarByNumber() throws Exception {

		addCar();

		MvcResult mvcResult = this.mockMvc.perform(get(PREFIX_URL + "car/get")
				.param("number", "q123we"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andDo(print())
				//.andExpect(content().json("{\"id\":\"c71062e1-7418-450c-a24c-0cc42f31aa4b\",\"number\":\"q123we\",\"make\":null,\"model\":null,\"owner\":null}"))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertNotNull(content);
		//,"number":"q123we",
		assertTrue(content.contains(",\"number\":\"q123we\","));
	}

}
