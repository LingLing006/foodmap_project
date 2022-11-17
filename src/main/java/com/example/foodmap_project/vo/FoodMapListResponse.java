package com.example.foodmap_project.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FoodMapListResponse {

	private String city;

	private List<FoodMapResponse> searchResList;

	private String message;

//============================================	
	
	public FoodMapListResponse() {

	}

	public FoodMapListResponse(String message) {
		this.message = message;
	}

	public List<FoodMapResponse> getSearchResList() {
		return searchResList;
	}

	public void setSearchResList(List<FoodMapResponse> searchResList) {
		this.searchResList = searchResList;
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
