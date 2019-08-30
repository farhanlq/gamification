package microservices.book.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.domain.ScoreCard;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	@Query("SELECT SUM(s.score FROM microservices.book.gamification.domain.ScoreCard s WHERe s.userId = :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") Long userId);

	@Query("SELECT NEW microservices.book.gamification.domain.LeaderBoard(s.userId, SUM(s.score))"
			+ "FROM microservices.book.gamification.domain.ScoreCard s "
			+ "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();

	
	
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long userId);
}
