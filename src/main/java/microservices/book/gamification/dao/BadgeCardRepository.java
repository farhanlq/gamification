package microservices.book.gamification.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.book.gamification.domain.BadgeCard;

@Repository
public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

	 List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
