package de.thm.arsnova.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/arsnova-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-main.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-security.xml",
		"file:src/test/resources/test-config.xml",
		"file:src/test/resources/test-socketioconfig.xml"
})
public class StatisticsControllerTest {

	@Autowired
	private StatisticsController statisticsController;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public final void testShouldGetCurrentOnlineUsers() throws Exception {
		mockMvc.perform(get("/statistics/activeusercount"))
		.andExpect(status().isOk())
		.andExpect(content().string("0"));
	}

	@Test
	public final void testShouldSendXDeprecatedApiForGetCurrentOnlineUsers() throws Exception {
		mockMvc.perform(get("/statistics/activeusercount"))
		.andExpect(status().isOk())
		.andExpect(header().string(AbstractController.X_DEPRECATED_API,"1"));
	}

	@Test
	public final void testShouldGetSessionCount() throws Exception {
		mockMvc.perform(get("/statistics/sessioncount"))
		.andExpect(status().isOk())
		.andExpect(content().string("3"));
	}

	@Test
	public final void testShouldSendXDeprecatedApiForGetSessionCount() throws Exception {
		mockMvc.perform(get("/statistics/sessioncount"))
		.andExpect(status().isOk())
		.andExpect(header().string(AbstractController.X_DEPRECATED_API,"1"));
	}

	@Test
	public final void testShouldGetStatistics() throws Exception {
		mockMvc.perform(get("/statistics"))
		.andExpect(status().isOk())
		.andExpect(content().string("{\"answers\":0,\"questions\":0,\"openSessions\":3,\"closedSessions\":0,\"activeUsers\":0}"));
	}

	@Test
	public final void testShouldGetCacheControlHeaderForStatistics() throws Exception {
		mockMvc.perform(get("/statistics"))
		.andExpect(status().isOk())
		.andExpect(header().string("cache-control", "public, max-age=60"));
	}
}
