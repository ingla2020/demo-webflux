package demo.webflux.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import demo.webflux.data.IwebRest;
import demo.webflux.model.Data;
import demo.webflux.model.ParameterUsers;
import demo.webflux.model.UserResponse;
import demo.webflux.model.UserResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TestWebClientServices {

	WebClientServices webClientServices;

	public IwebRest iwebRest;
	
	@BeforeEach
	public void config() {

		iwebRest = Mockito.mock(IwebRest.class);
		
		webClientServices = new WebClientServices(iwebRest);
		
	}
	
	@Test
	public void TestWebResult() {
		
		Data dat = new Data();
		dat.setId(1);
		dat.setEmail("leo@gmail.com");
		List<Data> listdata = new ArrayList<Data>();
		listdata.add(dat);

		Data dat2 = new Data();
		dat2.setId(2);
		dat2.setEmail("janet.weaver@reqres.in");
		listdata.add(dat2);

		
		
		UserResponse user = new UserResponse();
		user.setData(listdata);
		user.setPage(1);
		
		Mono<UserResponse> userresponse = Mono.just(user);
		
		Mockito.doReturn(userresponse).when(iwebRest).UserRest(1);
		
		 Mono<UserResult> flujo = webClientServices.WebResult(1);
		
	        StepVerifier.create(flujo)
	        .consumeNextWith(
	                response -> assertEquals("1", response.getPage().toString()))
	        .verifyComplete();

	}
	
	@Test
	public void TestUserAll() {

		TestWebResult();
		
		ParameterUsers pauser = new ParameterUsers();
		pauser.setPage(1);
		
		List<ParameterUsers> para = new ArrayList<ParameterUsers>();
		para.add(pauser);
		
		Flux<UserResult> flujo = webClientServices.UserAll(para);
		
        StepVerifier.create(flujo)
        .consumeNextWith(
                response -> assertEquals("1", response.getPage().toString()))
        .verifyComplete();		
		
	}

}
