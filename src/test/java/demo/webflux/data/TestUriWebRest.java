package demo.webflux.data;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUriWebRest {

	UriWebRest uriWebRest;
	
	@Test
	public void TestUriWebRest() {
		
		uriWebRest = new UriWebRest("http","localhost","8080","/api");
		
		assertEquals("http://localhost/api?page=1", uriWebRest.getEndPoint(1));
	}
	
	
}
