package microservices.book.gamification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

@Service
public class GameServiceImpl implements GameService {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GameServiceImpl.class);
	
	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;
	
	
	
	
	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
	
		if(correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id{}",userId,scoreCard.getScore(),attemptId );
			List<BadgeCard> badgeCards = processForBadges(userId,attemptId);
			
			return new GameStats(userId, scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		
		}
		return GameStats.emptyStats(userId);
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		return null;
	}

}
