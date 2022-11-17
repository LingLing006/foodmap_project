package com.example.foodmap_project.vo;

import java.util.List;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapShop;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FoodMapResponse {

	@JsonProperty("shop_name")
	private String shopName;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("shop_level")
	private double shopLevel;

	private FoodMapShop shop;

	private FoodMapMeal meal;

	@JsonProperty("city")
	private String city;

	private String message;

	private List<FoodMapMeal> mealList;

//===========================================	
	
	public FoodMapResponse() {

	}

	public FoodMapResponse(String message) {
		this.message = message;
	}

	public FoodMapResponse(FoodMapShop shop, String message) {
		this.shop = shop;
		this.message = message;
	}

	public FoodMapResponse(FoodMapMeal meal, String message) {
		this.meal = meal;
		this.message = message;
	}

	public FoodMapResponse(List<FoodMapMeal> mealList, String shopName, double shopLevel) {
		this.mealList = mealList;
		this.shopName = shopName;
		this.shopLevel = shopLevel;
	}

	public FoodMapResponse(List<FoodMapMeal> mealList, FoodMapShop shop) {
		this.mealList = mealList;
		this.shop = shop;
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

	public List<FoodMapMeal> getMealList() {
		return mealList;
	}

	public void setMealList(List<FoodMapMeal> mealList) {
		this.mealList = mealList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public FoodMapShop getShop() {
		return shop;
	}

	public void setShop(FoodMapShop shop) {
		this.shop = shop;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FoodMapMeal getMeal() {
		return meal;
	}

	public void setMeal(FoodMapMeal meal) {
		this.meal = meal;
	}

}
