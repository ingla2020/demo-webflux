package demo.webflux.data;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.Duration;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.SocketUtils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import demo.webflux.model.UserResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class TestWebRestUser {

    @Mock
    private static WebClient webClient;
    
	static UriWebRest uriWebRest;	
    
    static WebRestUser webRestUser;
    
    static WireMockServer wireMockServer;
	/**/
	
    
    static int WIREMOCK_PORT = SocketUtils.findAvailableTcpPort();
    
    @BeforeAll
    static void sedall() {
		uriWebRest = Mockito.mock(UriWebRest.class);
		
		webClient = WebClient
		.builder()
		//.baseUrl("localhost/")
		.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		.build();    	

		
		webRestUser = new WebRestUser(webClient, uriWebRest);

        System.out.println("init iiiiiiiiiiiiiiiiiiiiiiiiiserver");
		wireMockServer = new WireMockServer(WIREMOCK_PORT);    
	    wireMockServer.start();

    }
    
	@BeforeEach
	public void setup() {
		
		//initServer("/api", 200);			    

	}
	
	
	@AfterAll
	static void afterEach() 
	{
		wireMockServer.shutdownServer();
	}
	
	
	public void initServer(String url, int status) {
	   // wireMockServer.start();  
	    
        MappingBuilder mockReq = get("/api");


        ResponseDefinitionBuilder mockResponse =
                new ResponseDefinitionBuilder();
        // 200 ok
        mockResponse.withStatus(status)
                .withBody(JSON)
                .withHeader("Content-Type", "application/json");

        WireMock.configureFor("localhost", WIREMOCK_PORT);
        WireMock.stubFor(

                mockReq.willReturn(mockResponse));		
	}
	
	@Test
	public void TestUserRest() {


		initServer("/api", 200);	

		Mockito.doReturn("http://localhost:"+ WIREMOCK_PORT +"/api").when(uriWebRest).getEndPoint(1);
		

		Mono<UserResponse> flujo = webRestUser.UserRest(1);

        StepVerifier.create(flujo)
        .consumeNextWith(
                response -> assertEquals("2", response.getPage().toString()))
        .verifyComplete();

	}
	
	
	@Test
	public void TestException() {
		
		wireMockServer.resetAll();
		initServer("/xapi", 404);
		
		Mockito.doReturn("http://localhost:"+ WIREMOCK_PORT +"/api").when(uriWebRest).getEndPoint(1);
		
		Mono<UserResponse> flujo = webRestUser.UserRest(1);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            flujo.block();
        });

        String expectedMessage = "Error while calling  accounts endpoint";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));        
        
	}
	
	
	
public final static String JSON = "{\r\n"
		+ "\"page\": 2,\r\n"
		+ "\"per_page\": 6,\r\n"
		+ "\"total\": 12,\r\n"
		+ "\"total_pages\": 2,\r\n"
		+ "\"data\": [\r\n"
		+ "{\r\n"
		+ "\"id\": 7,\r\n"
		+ "\"email\": \"michael.lawson@reqres.in\",\r\n"
		+ "\"first_name\": \"Michael\",\r\n"
		+ "\"last_name\": \"Lawson\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/7-image.jpg\"\r\n"
		+ "},\r\n"
		+ "{\r\n"
		+ "\"id\": 8,\r\n"
		+ "\"email\": \"lindsay.ferguson@reqres.in\",\r\n"
		+ "\"first_name\": \"Lindsay\",\r\n"
		+ "\"last_name\": \"Ferguson\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/8-image.jpg\"\r\n"
		+ "},\r\n"
		+ "{\r\n"
		+ "\"id\": 9,\r\n"
		+ "\"email\": \"tobias.funke@reqres.in\",\r\n"
		+ "\"first_name\": \"Tobias\",\r\n"
		+ "\"last_name\": \"Funke\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/9-image.jpg\"\r\n"
		+ "},\r\n"
		+ "{\r\n"
		+ "\"id\": 10,\r\n"
		+ "\"email\": \"byron.fields@reqres.in\",\r\n"
		+ "\"first_name\": \"Byron\",\r\n"
		+ "\"last_name\": \"Fields\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/10-image.jpg\"\r\n"
		+ "},\r\n"
		+ "{\r\n"
		+ "\"id\": 11,\r\n"
		+ "\"email\": \"george.edwards@reqres.in\",\r\n"
		+ "\"first_name\": \"George\",\r\n"
		+ "\"last_name\": \"Edwards\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/11-image.jpg\"\r\n"
		+ "},\r\n"
		+ "{\r\n"
		+ "\"id\": 12,\r\n"
		+ "\"email\": \"rachel.howell@reqres.in\",\r\n"
		+ "\"first_name\": \"Rachel\",\r\n"
		+ "\"last_name\": \"Howell\",\r\n"
		+ "\"avatar\": \"https://reqres.in/img/faces/12-image.jpg\"\r\n"
		+ "}\r\n"
		+ "],\r\n"
		+ "\"support\": {\r\n"
		+ "\"url\": \"https://reqres.in/#support-heading\",\r\n"
		+ "\"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\r\n"
		+ "}\r\n"
		+ "}";
	
}
