package com.example.foodmap_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FoodMapListResponse {
	
	private String city;
	private List<FoodMapResponse> resList;
	
	private String message;
	
	
//============================================	
	public FoodMapListResponse() {
		
	}
	public FoodMapListResponse(String message) {
		this.message = message;
	}

	public List<FoodMapResponse> getResList() {
		return resList;
	}

	public void setResList(List<FoodMapResponse> resList) {
		this.resList = resList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
