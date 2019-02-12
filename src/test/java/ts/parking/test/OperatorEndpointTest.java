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
public class OperatorEndpointTest {

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
	public void getAllRepository() throws Exception {	
		mockMvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(content()
					.json("[{\"vehiclePlateNumber\":\"wwe43pw\",\"driverType\":\"Disabled\","
							+ "\"payment\":{\"paymentDateTime\":\"2019-02-07T10:30:00\","
							+ "\"paymentAmount\":0.00,\"currency\":\"PLN\"},"
							+ "\"startDateTime\":\"2019-02-07T10:30:00\",\"endDateTime\":\"2019-02-07T11:30:00\"},"
							+"{\"vehiclePlateNumber\":\"wo123pe\",\"driverType\":\"Regular\","
							+ "\"payment\":{\"paymentDateTime\":\"2019-02-07T11:30:00\","
							+ "\"paymentAmount\":6.00,\"currency\":\"PLN\"},"
							+ "\"startDateTime\":\"2019-02-07T11:30:00\",\"endDateTime\":\"2019-02-07T14:30:00\"}]"));
	}
	
	@Test
	public void whenGivenPlateNumber_thenGetParkingDetails() throws Exception {
		mockMvc.perform(get("/admin/wwe43pw"))
			.andExpect(status().isOk())
			.andExpect(content()
					.json("{\"vehiclePlateNumber\":\"wwe43pw\",\"driverType\":\"Disabled\","
							+ "\"payment\":{\"paymentDateTime\":\"2019-02-07T10:30:00\","
							+ "\"paymentAmount\":0.00,\"currency\":\"PLN\"},"
							+ "\"startDateTime\":\"2019-02-07T10:30:00\",\"endDateTime\":\"2019-02-07T11:30:00\"}"));
	}
	
	@Test
	public void whenGivenDate_thenGetAmountValueFromDate() throws Exception {
		mockMvc.perform(get("/admin/2019/2/7")).andExpect(status().isOk()).andExpect(content().string("6.00"));
	}	
}
