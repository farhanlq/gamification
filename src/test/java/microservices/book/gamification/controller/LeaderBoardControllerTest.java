package microservices.book.gamification.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {

	@MockBean
	private LeaderBoardService leaderBoardService;

	private JacksonTester<List<LeaderBoardRow>> jsonLeaderBoardRowList;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getLeaderBoardTest() throws Exception {

		LeaderBoardRow leader1 = new LeaderBoardRow(1L, 540L);
		LeaderBoardRow leader2 = new LeaderBoardRow(2L, 444L);
		List<LeaderBoardRow> leaderBoardList = new ArrayList<>();
		Collections.addAll(leaderBoardList, leader1, leader2);

		when(leaderBoardService.getCurrentLeaderBoard()).thenReturn(leaderBoardList);

		MockHttpServletResponse response = mockMvc.perform(get("/leaders").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(jsonLeaderBoardRowList.write(leaderBoardList).getJson());

	}

}
