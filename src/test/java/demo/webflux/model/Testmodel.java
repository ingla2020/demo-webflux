package demo.webflux.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Testmodel {

	@Test
	public void TestUserResponse() {

		Support support = new Support();
		
		List<Data> listdata = new ArrayList<Data>();
		
		
		UserResponse user = new UserResponse();
		user.setPage(1);
		user.setPer_page(1);
		user.setSupport(support);
		user.setTotal(1);
		user.setTotal_pages(1);
		user.setData(listdata);
		
		assertEquals(user.getData(), listdata);
		assertEquals(user.getPage().toString(), "1");
		assertEquals(user.getPer_page().toString(), "1");
		assertEquals(user.getSupport(), support);
		assertEquals(user.getTotal().toString(), "1");
		assertEquals(user.getTotal_pages().toString(), "1");
		assertEquals(user.getData(),listdata);
		
	}

	
	@Test
	public void TestSupport() {
		Support support = new Support();
		support.setText("T");
		support.setUrl("T");

		assertEquals(support.getText(), "T");
		assertEquals(support.getUrl(), "T");
		
	}
	
	
	@Test
	public void TestData() {
		Data data = new Data();
		data.setAvatar("T");
		data.setEmail("T");
		data.setFirst_name("T");
		data.setId(1);
		data.setLast_name("T");

		assertEquals(data.getAvatar(), "T");
		assertEquals(data.getEmail(), "T");
		assertEquals(data.getFirst_name(), "T");
		assertEquals(data.getId().toString(), "1");
		assertEquals(data.getLast_name(), "T");
		
	}	
	
	
	@Test
	public void TestUserResult() {
		
		Map<Integer, Data> mapdata = new HashMap<>();
		mapdata.put(1,new Data());
		
		UserResult user = new UserResult();
		user.setData(mapdata);
		user.setPage(1);
		
		assertEquals(user.getData(), mapdata);
		assertEquals(user.getPage().toString(), "1");
	}
	
	
}
