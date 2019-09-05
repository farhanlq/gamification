package microservices.book.gamification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservices.book.gamification.dao.ScoreCardRepository;
import microservices.book.gamification.domain.LeaderBoardRow;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

	
	private ScoreCardRepository scoreCardRepository;

	@Autowired
	LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
