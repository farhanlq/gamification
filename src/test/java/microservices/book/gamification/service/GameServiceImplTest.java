package microservices.book.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

public class GameServiceImplTest {

	private GameServiceImpl gameService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Mock
	private BadgeCardRepository badgeCardRepository;

	@Mock
	private MultiplicationResultAttemptClient multiplicationClient;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository, multiplicationClient);

		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("farhan_laeeq", 20, 70, 1400, true);
		when(multiplicationClient.retrieveMultiplicationResultAttemptbyId(Mockito.anyLong())).thenReturn(attempt);
	}

	@Test
	public void processFirstCorrectAttemptTest() {
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);

		when(scoreCardRepository.getTotalScoreForUser(userId)).thenReturn(totalScore);

		when(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.thenReturn(Collections.singletonList(scoreCard));

		when(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).thenReturn(Collections.emptyList());

		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, true);

		assertThat(gameStats.getScore()).isEqualTo(scoreCard.getScore());

		assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);
	}

	@Test
	public void processCorrectAttemptForScoreBadgeTest() {
		Long userId = 1L;
		Long attemptId = 29L;
		int totalScore = 100;
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		when(scoreCardRepository.getTotalScoreForUser(userId)).thenReturn(totalScore);
		when(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.thenReturn(createNScoreCards(10, userId));
		when(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.thenReturn(Collections.singletonList(firstWonBadge));

		GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

		assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(iteration.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
	}

	@Test
	public void processCorrectAttemptForLuckyNumberBadgeTest() {
		Long userId = 1L;
		Long attemptId = 29L;
		int totalScore = 10;
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		when(scoreCardRepository.getTotalScoreForUser(userId)).thenReturn(totalScore);
		when(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.thenReturn(createNScoreCards(1, userId));
		when(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.thenReturn(Collections.singletonList(firstWonBadge));
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt("farhan_laeeq", 42, 10, 420, true);
		when(multiplicationClient.retrieveMultiplicationResultAttemptbyId(attemptId)).thenReturn(attempt);

		GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);
		assertThat(iteration.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(iteration.getBadges()).containsOnly(Badge.LUCKY_NUMBER);
	}

	@Test
	public void processWrongAttemptTest() {
		Long userId = 1L;
		Long attemptId = 8L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		when(scoreCardRepository.getTotalScoreForUser(userId)).thenReturn(totalScore);

		when(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.thenReturn(Collections.singletonList(scoreCard));

		when(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).thenReturn(Collections.emptyList());

		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, false);

		assertThat(gameStats.getScore()).isEqualTo(0);

		assertThat(gameStats.getBadges()).isEmpty();

	}

	@Test
	public void retrieveStatsForUserTest() {
		Long userId = 1L;
		int totalScore = 1000;
		BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
		when(scoreCardRepository.getTotalScoreForUser(userId)).thenReturn(totalScore);
		when(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.thenReturn(Collections.singletonList(badgeCard));

		GameStats stats = gameService.retrieveStatsForUser(userId);

		assertThat(stats.getScore()).isEqualTo(totalScore);
		assertThat(stats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
	}

	private List<ScoreCard> createNScoreCards(int n, Long userId) {
		return IntStream.range(0, n).mapToObj(i -> new ScoreCard(userId, (long) i)).collect(Collectors.toList());
	}

}
