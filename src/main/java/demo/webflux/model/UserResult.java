package demo.webflux.model;

import java.util.List;
import java.util.Map;

public class UserResult {
	
	private Integer page;
	private Map<Integer, Data> data;
	
	public UserResult() {
	}

	public UserResult(Integer page, Map<Integer, Data> data) {
		super();
		this.page = page;
		this.data = data;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Map<Integer, Data> getData() {
		return data;
	}

	public void setData(Map<Integer, Data> data) {
		this.data = data;
	}




	
	
}
