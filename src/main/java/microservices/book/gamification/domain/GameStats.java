package microservices.book.gamification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameStats {

	private Long userId;

	private int score;

	private List<Badge> badges;

	public GameStats() {
		this.userId = 0L;
		this.score = 0;
		this.badges = new ArrayList<>();
	}

	public GameStats(Long userId, int score, List<Badge> badges) {
		this.userId = userId;
		this.score = score;
		this.badges = badges;
	}

	public Long getUserId() {
		return userId;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badges == null) ? 0 : badges.hashCode());
		result = prime * result + score;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameStats other = (GameStats) obj;
		if (badges == null) {
			if (other.badges != null)
				return false;
		} else if (!badges.equals(other.badges))
			return false;
		if (score != other.score)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameStats [userId=" + userId + ", score=" + score + ", badges=" + badges + "]";
	}

	public static GameStats emptyStats(final Long userId) {
		return new GameStats(userId, 0, Collections.emptyList());
	}

	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}

}
