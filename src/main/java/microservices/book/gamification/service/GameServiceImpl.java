package microservices.book.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

@Service
public class GameServiceImpl implements GameService {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GameServiceImpl.class);

	private static final int LUCKY_NUMBER = 42;

	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;
	private MultiplicationResultAttemptClient attemptClient;

	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository,
			MultiplicationResultAttemptClient attemptClient) {
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
		this.attemptClient = attemptClient;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {

		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id{}", userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);

			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));

		}
		return GameStats.emptyStats(userId);
	}

	private List<BadgeCard> processForBadges(Long userId, Long attemptId) {
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);

		List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
				.ifPresent(badgeCards::add);

		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
				.ifPresent(badgeCards::add);

		checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
				.ifPresent(badgeCards::add);

		// First Won Badge
		if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);

			badgeCards.add(firstWonBadge);
		}

		// Lucky Number Badge
		MultiplicationResultAttempt attempt = attemptClient.retrieveMultiplicationResultAttemptbyId(attemptId);
		if (containsBadge(badgeCardList, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER == attempt.getMulitplicationFactorA()
				|| LUCKY_NUMBER == attempt.getMultiplicationFactorB())) {
			BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
			badgeCards.add(luckyNumberBadge);
		}
		return badgeCards;
	}

	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCardList, Badge badge, int score,
			int scoreThreshold, Long userId) {
		if (score >= scoreThreshold && !containsBadge(badgeCardList, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new badge: {}", userId, badge);
		return badgeCard;
	}

	private boolean containsBadge(List<BadgeCard> badgeCardList, Badge badge) {
		return badgeCardList.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		return null;
	}

}
