package microservices.book.gamification.client;

import org.springframework.stereotype.Component;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;


public interface MultiplicationResultAttemptClient {

	MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId( Long multiplicationId);	
}
