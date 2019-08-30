package microservices.book.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ScoreCard {

	public static final int DEFAULT_SCORE = 20;
	
	@Id
	@GeneratedValue
	@Column(name = "CARD_ID")
	private Long cardId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "ATTEMPT_ID")
	private Long attemptId;

	@Column(name = "SCORE_TS")
	private long scoreTimeStamp;

	@Column(name = "SCORE")
	private int score;

	public ScoreCard() {
	}

	public ScoreCard(Long cardId, Long userId, Long attemptId, long scoreTimeStamp, int score) {
		this.cardId = null;
		this.userId = userId;
		this.attemptId = attemptId;
		this.scoreTimeStamp = System.currentTimeMillis();
		this.score = DEFAULT_SCORE;
	}

	public Long getCardId() {
		return cardId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getAttemptId() {
		return attemptId;
	}

	public long getScoreTimeStamp() {
		return scoreTimeStamp;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attemptId == null) ? 0 : attemptId.hashCode());
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
		result = prime * result + score;
		result = prime * result + (int) (scoreTimeStamp ^ (scoreTimeStamp >>> 32));
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
		ScoreCard other = (ScoreCard) obj;
		if (attemptId == null) {
			if (other.attemptId != null)
				return false;
		} else if (!attemptId.equals(other.attemptId))
			return false;
		if (cardId == null) {
			if (other.cardId != null)
				return false;
		} else if (!cardId.equals(other.cardId))
			return false;
		if (score != other.score)
			return false;
		if (scoreTimeStamp != other.scoreTimeStamp)
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
		return "ScoreCard [cardId=" + cardId + ", userId=" + userId + ", attemptId=" + attemptId + ", scoreTimeStamp="
				+ scoreTimeStamp + ", score=" + score + "]";
	}

}
