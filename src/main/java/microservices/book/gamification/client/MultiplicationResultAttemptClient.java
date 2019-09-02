package microservices.book.gamification.client;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

public interface MultiplicationResultAttemptClient {

	MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId(Long multiplicationId);
}
