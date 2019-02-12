package ts.parking.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ts.parking.controller.Application;
import ts.parking.model.ParkingDAO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DriverEndpointTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ParkingDAO parkingRepository;
	
	@Before
	public void setUp() {
		parkingRepository.startParking("wwe43pw", "2019-02-07-10-30", "2019-02-07-11-30", "Disabled");
		parkingRepository.startParking("wo123pe", "2019-02-07-11-30", "2019-02-07-14-30", "Regular");	
	}
	
	@After
	public void cleanUp() {
		parkingRepository.clearData();
	}
	
	@Test
	public void whenNewParkStarted_thenAddedToRepository() throws Exception {
		mockMvc.perform(post("/park")
				.param("vehiclePlateNumber", "wi1234")
				.param("startDateTime","2019-02-07-10-30")
				.param("endDateTime","2019-02-07-11-30")
				.param("driverType", "Regular"))
		.andExpect(status().isOk()).andExpect(content().json("{\"vehiclePlateNumber\":\"wi1234\",\"driverType\":\"Regular\","
				+ "\"payment\":{\"paymentDateTime\":\"2019-02-07T10:30:00\","
				+ "\"paymentAmount\":1.00,\"currency\":\"PLN\"},"
				+ "\"startDateTime\":\"2019-02-07T10:30:00\",\"endDateTime\":\"2019-02-07T11:30:00\"}"));
	}
	
	@Test
	public void whenDriverPlateNumber_thenGetParkingAmount() throws Exception {
		mockMvc.perform(get("/park/wo123pe")).andExpect(status().isOk()).andExpect(content().string("6.00"));
	}
}
