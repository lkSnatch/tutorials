package net.snatchTech.cacheSimple;

import net.snatchTech.cacheSimple.api.CarController;
import net.snatchTech.cacheSimple.dao.CacheDao;
import net.snatchTech.cacheSimple.dao.FakeCarDao;
import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.model.CarOwner;
import net.snatchTech.cacheSimple.service.CarService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CacheTests {

	@Autowired
	private MockMvc mockMvc;
	private final String PREFIX_URL = "/api/v1/";

	//@BeforeAll
	@BeforeEach
	public void fillDB() throws Exception {
		addCar("q911qq", "porsche", "911");
		addCar("a222qq", "bmv", "x1");
		addCar("z333qq", "lambo", "ff");
		addPerson("Lex Lutor", "01011980");
		addPerson("Hex Editor", "10101977");
		addPerson("Felix Cat", "12122001");
	}

	/*@AfterEach
	public void after() throws NoSuchFieldException, IllegalAccessException {
		clearCache();
	}*/

	private void clearCache() throws NoSuchFieldException, IllegalAccessException {
		Field cacheField = CacheDao.class.getDeclaredField("cache");
		cacheField.setAccessible(true);
		Map<String, CarOwner> cache = (Map<String, CarOwner>) cacheField.get(null);
		cache.clear();
	}

	private void writeAllData() throws Exception {
		// write different records
		setOwner("q911qq", "Lex Lutor");
		setOwner("a222qq", "Hex Editor");
		setOwner("z333qq", "Felix Cat");
	}

	private void readAllData() throws Exception {
		// read different records first time
		getOwner("q911qq");
		getOwner("a222qq");
		getOwner("z333qq");
		// read again same three records
		getOwner("q911qq");
		getOwner("a222qq");
		getOwner("z333qq");
	}

	@Test
	public void readAll() throws Exception {
		// set owners and clean the cache
		writeAllData();
		clearCache();

		long time = System.nanoTime();
		readAllData();
		System.out.println("read time: " + ((System.nanoTime() - time)/1000000000d));
	}

	@Test
	public void writeAll() throws Exception {
		long time = System.nanoTime();
		writeAllData();
		System.out.println("write time: " + ((System.nanoTime() - time)/1000000000d));
	}

	@Test
	public void readAfterWrite() throws Exception {
		writeAllData();

		long time = System.nanoTime();
		readAllData();
		System.out.println("read after write time: " + ((System.nanoTime() - time)/1000000000d));
	}

	@Test
	public void writeAfterRead() throws Exception {
		readAllData();

		long time = System.nanoTime();
		writeAllData();
		System.out.println("write after read time: " + ((System.nanoTime() - time)/1000000000d));
	}

	private void addCar(String number, String make, String model) throws Exception {
		this.mockMvc.perform(post(PREFIX_URL + "car/addWithModel")
				.param("number", number)
				.param("make", make)
				.param("model", model));
	}

	private void addPerson(String name, String birthday) throws Exception {
		this.mockMvc.perform(post(PREFIX_URL + "person/addWithBirthday")
				.param("name", name)
				.param("birthday", birthday));
	}

	private void setOwner(String number, String name) throws Exception {
		this.mockMvc.perform(patch(PREFIX_URL + "setOwner")
				.param("number", number)
				.param("name", name));
	}

	private void getOwner(String number) throws Exception {
		this.mockMvc.perform(get(PREFIX_URL + "getOwner")
				.param("number", number));
				//.andDo(print());
	}

}
