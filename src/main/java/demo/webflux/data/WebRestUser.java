package demo.webflux.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import demo.webflux.model.UserResponse;
import reactor.core.publisher.Mono;

@Repository
public class WebRestUser implements IwebRest{

	public WebClient webclient;

	UriWebRest uriWebRest;	
	
	public WebRestUser(WebClient webclientx, UriWebRest uriWebRest) {
		this.uriWebRest = uriWebRest;
		this.webclient = webclientx;
	}


	@Override
	public Mono<UserResponse> UserRest(Integer id) {
		
		System.out.println(uriWebRest.getEndPoint(id));
		
		return webclient
				.get()
				.uri(uriWebRest.getEndPoint(id))
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> {
					
			
                    throw new RuntimeException(
                            "Error while calling  accounts endpoint");
				})
				.bodyToMono(UserResponse.class)
				;
				
		
	}

}
