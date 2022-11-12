package com.example.foodmap_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapRequest2 {

	@JsonProperty(value = "city")
	private String city;

	@JsonProperty(value = "shop_name")
	private String shopName;
	
	@JsonProperty(value = "new_shop_name")
	private String newShopName;
	
	@JsonProperty(value = "new_meal_name")
	private String newMealName;

	@JsonProperty(value = "meal_name")
	private String mealName;

//====================================	
	public FoodMapRequest2() {

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}



	public String getNewShopName() {
		return newShopName;
	}

	public void setNewShopName(String newShopName) {
		this.newShopName = newShopName;
	}

	public String getNewMealName() {
		return newMealName;
	}

	public void setNewMealName(String newMealName) {
		this.newMealName = newMealName;
	}

	

}
