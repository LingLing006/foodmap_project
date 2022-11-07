package com.example.foodmap_project.vo;

import java.util.List;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FoodMapResponse {

	@JsonProperty("shop_name")
	private String shopName;
	
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("shop_level")
	private double shopLevel;
	
	private FoodMap_Shop shop;
	
	private FoodMap_Meal meal;
	
	@JsonProperty("city")
	private String city;
	
	private String message;
	
	private List<FoodMap_Meal> MealList;

//===========================================	
	public FoodMapResponse() {
		
	}
	public FoodMapResponse(String message) {
		this.message = message;
	}
	
	public FoodMapResponse(FoodMap_Shop shop,String message) {
		this.shop = shop;
		this.message = message;
	}
	public FoodMapResponse(FoodMap_Meal meal,String message) {
		this.meal = meal;
		this.message = message;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public double getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(double shopLevel) {
		this.shopLevel = shopLevel;
	}

	public List<FoodMap_Meal> getMealList() {
		return MealList;
	}

	public void setMealList(List<FoodMap_Meal> mealList) {
		MealList = mealList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public FoodMap_Shop getShop() {
		return shop;
	}

	public void setShop(FoodMap_Shop shop) {
		this.shop = shop;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public FoodMap_Meal getMeal() {
		return meal;
	}
	public void setMeal(FoodMap_Meal meal) {
		this.meal = meal;
	}

	
}
