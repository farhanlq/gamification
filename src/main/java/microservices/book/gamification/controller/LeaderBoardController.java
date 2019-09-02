package microservices.book.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

	private LeaderBoardService leaderBoardService;

	public LeaderBoardController(LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}

	public List<LeaderBoardRow> getLeaderBoard() {
		return leaderBoardService.getCurrentLeaderBoard();
	}

}
