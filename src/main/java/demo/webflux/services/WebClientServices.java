package demo.webflux.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.webflux.data.IwebRest;
import demo.webflux.model.Data;
import demo.webflux.model.ParameterUsers;
import demo.webflux.model.UserResponse;
import demo.webflux.model.UserResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class WebClientServices implements IWebClientServices{

	@Autowired
	public IwebRest iwebRest;
	
	@Override
	public Mono<UserResult> WebResult(Integer id) {
		
		return iwebRest
				.UserRest(id)
				.map(map -> {

					return new UserResult(map.getPage(), 
														map
														.getData()
														.stream()
														.collect(Collectors
																	.toMap(Data::getId, 
																			Function.identity())
																)
												);
				});
	}
	
	
	@Override
	public Flux<UserResult> UserAll(List<ParameterUsers> para) {
		return Flux.range(1, para.size())
				.flatMapSequential(numero-> {
					return Flux.range(numero, 1)
							.flatMap(fla-> {
								return WebResult(para.get(numero - 1).getPage());
							})
							.map(result-> {
								
								return new UserResult(result.getPage() ,
									result.getData()
									.entrySet()
									.stream()
									.filter(fil-> !fil.getValue().getEmail().equals("janet.weaver@reqres.in"))
									.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
									);
								
							})
							.subscribeOn(Schedulers.newParallel("Thread", 1));
				});	
				
	}

	
	
}
