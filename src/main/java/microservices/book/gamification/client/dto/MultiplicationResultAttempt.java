package microservices.book.gamification.client.dto;


public class MultiplicationResultAttempt {

	private String userAlias;
	
	private int mulitplicationFactorA;
	private int multiplicationFactorB;
	private int resultAttempt;
	
	private boolean correct;
	 
	public MultiplicationResultAttempt() {
		userAlias = null;
		mulitplicationFactorA = -1;
		multiplicationFactorB = -1;
		resultAttempt = -1;
		correct = false;
	}

	public MultiplicationResultAttempt(String userAlias, int mulitplicationFactorA, int multiplicationFactorB,
			int resultAttempt, boolean correct) {
		this.userAlias = userAlias;
		this.mulitplicationFactorA = mulitplicationFactorA;
		this.multiplicationFactorB = multiplicationFactorB;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public int getMulitplicationFactorA() {
		return mulitplicationFactorA;
	}

	public int getMultiplicationFactorB() {
		return multiplicationFactorB;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}

	public boolean isCorrect() {
		return correct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + mulitplicationFactorA;
		result = prime * result + multiplicationFactorB;
		result = prime * result + resultAttempt;
		result = prime * result + ((userAlias == null) ? 0 : userAlias.hashCode());
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
		MultiplicationResultAttempt other = (MultiplicationResultAttempt) obj;
		if (correct != other.correct)
			return false;
		if (mulitplicationFactorA != other.mulitplicationFactorA)
			return false;
		if (multiplicationFactorB != other.multiplicationFactorB)
			return false;
		if (resultAttempt != other.resultAttempt)
			return false;
		if (userAlias == null) {
			if (other.userAlias != null)
				return false;
		} else if (!userAlias.equals(other.userAlias))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MultiplicationResultAttempt [userAlias=" + userAlias + ", mulitplicationFactorA="
				+ mulitplicationFactorA + ", multiplicationFactorB=" + multiplicationFactorB + ", resultAttempt="
				+ resultAttempt + ", correct=" + correct + "]";
	}
	
	
}
