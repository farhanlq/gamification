package microservices.book.gamification.client.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import microservices.book.gamification.client.MultiplicationResultAttempt;

@Component
public class MultiplicationResultAttemptImpl implements MultiplicationResultAttempt {

	private RestTemplate restTemplate;
	private String multiplicationHost;

	@Autowired
	public MultiplicationResultAttemptImpl(RestTemplate restTemplate,
			@Value("${multiplicationHost}") String multiplicationHost) {
		this.restTemplate = restTemplate;
		this.multiplicationHost = multiplicationHost;
	}

	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId(Long multiplicationId) {
		return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationId,
				MultiplicationResultAttempt.class);
	}

}
