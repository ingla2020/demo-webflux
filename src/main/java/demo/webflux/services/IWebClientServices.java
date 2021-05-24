package demo.webflux.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import demo.webflux.model.ParameterUsers;
import demo.webflux.model.UserResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IWebClientServices {

	public Mono<UserResult> WebResult(Integer id);
	public Flux<UserResult> UserAll(List<ParameterUsers> para);
}
