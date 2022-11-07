package com.example.foodmap_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodMapRequest {

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

	@JsonProperty(value = "price")
	private int price;

	@JsonProperty(value = "meal_level")
	private int mealLevel;
	
	@JsonProperty(value = "shop_level")
	private int shopLevel;
	
	@JsonProperty(value = "display_amount")
	private int displayAmount;

//====================================	
	public FoodMapRequest() {

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMealLevel() {
		return mealLevel;
	}

	public void setMealLevel(int mealLevel) {
		this.mealLevel = mealLevel;
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

	public int getDisplayAmount() {
		return displayAmount;
	}

	public void setDisplayAmount(int displayAmount) {
		this.displayAmount = displayAmount;
	}

	public int getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(int shopLevel) {
		this.shopLevel = shopLevel;
	}
	

}
