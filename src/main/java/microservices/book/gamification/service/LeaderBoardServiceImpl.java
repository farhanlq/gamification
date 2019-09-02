package microservices.book.gamification.service;

import java.util.List;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.repository.ScoreCardRepository;

public class LeaderBoardServiceImpl implements LeaderBoardService {

	private ScoreCardRepository scoreCardRepository;

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
