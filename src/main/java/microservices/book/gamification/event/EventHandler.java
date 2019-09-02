package microservices.book.gamification.event;

import org.slf4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

import microservices.book.gamification.service.GameService;

@Component
public class EventHandler {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventHandler.class);

	private GameService gameService;

	public EventHandler(GameService gameService) {
		this.gameService = gameService;
	}

	public void handleMultiplicationSolved(MultiplicationSolvedEvent event) {
		log.info("Multiplication Solved Event received: {}", event.getMultiplicationResultAttemptId());

		try {
			gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(),
					event.isCorrect());
		} catch (Exception e) {
			log.error("Error when trying to process MultiplicationSolvedEvent", e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}

}
