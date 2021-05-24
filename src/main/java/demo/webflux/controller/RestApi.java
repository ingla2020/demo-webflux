package demo.webflux.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.webflux.model.ParameterUsers;
import demo.webflux.model.UserResult;
import demo.webflux.services.IWebClientServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RestApi {

	@Autowired
	public IWebClientServices iWebClientServices;
	
	@GetMapping(path = "id/{id}", 
				consumes = "application/json",
				produces = "application/json"
				)
	public Mono<UserResult> ApiUserResult(
				@PathVariable("id") Integer id
			)
	{
		return iWebClientServices.WebResult(id);
		
	}
	
	@GetMapping(path = "pageall",
			consumes = "application/json",
			produces = "application/json")
	public Flux<UserResult> UserAll(@RequestBody List<ParameterUsers> para){
		return iWebClientServices.UserAll(para);
		
	}
	
	
}
