package microservices.book.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import microservices.book.gamification.domain.BadgeCard;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
