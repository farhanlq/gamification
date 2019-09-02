package microservices.book.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BadgeCard {

	@Id
	@GeneratedValue
	@Column(name = "BADGE_ID")
	private Long badgeId;

	private Long userId;

	private long badgeTimeStamp;

	private Badge badge;

	public BadgeCard() {
	}

	public BadgeCard(Long userId,  Badge badge) {
		this.badgeId = null;
		this.userId = userId;
		this.badgeTimeStamp = System.currentTimeMillis();
		this.badge = badge;
	}

	public Long getBadgeId() {
		return badgeId;
	}

	public Long getUserId() {
		return userId;
	}

	public long getBadgeTimeStamp() {
		return badgeTimeStamp;
	}

	public Badge getBadge() {
		return badge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badge == null) ? 0 : badge.hashCode());
		result = prime * result + ((badgeId == null) ? 0 : badgeId.hashCode());
		result = prime * result + (int) (badgeTimeStamp ^ (badgeTimeStamp >>> 32));
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
		BadgeCard other = (BadgeCard) obj;
		if (badge != other.badge)
			return false;
		if (badgeId == null) {
			if (other.badgeId != null)
				return false;
		} else if (!badgeId.equals(other.badgeId))
			return false;
		if (badgeTimeStamp != other.badgeTimeStamp)
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
		return "BadgeCard [badgeId=" + badgeId + ", userId=" + userId + ", badgeTimeStamp=" + badgeTimeStamp
				+ ", badge=" + badge + "]";
	}

}
