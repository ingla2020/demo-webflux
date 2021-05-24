package demo.webflux.data;

import demo.webflux.model.UserResponse;
import reactor.core.publisher.Mono;

public interface IwebRest {

	public  Mono<UserResponse> UserRest(Integer id); 
	
}
