package microservices.book.gamification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import microservices.book.gamification.dao.ScoreCardRepository;
import microservices.book.gamification.domain.LeaderBoardRow;

public class LeaderBoardServiceImpl implements LeaderBoardService {

	@Autowired
	private ScoreCardRepository scoreCardRepository;

	LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
