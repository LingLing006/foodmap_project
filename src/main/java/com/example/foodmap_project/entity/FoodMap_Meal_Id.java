package com.example.foodmap_project.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FoodMap_Meal_Id implements Serializable{

	private String shopName;
	private String mealName;
	
	public FoodMap_Meal_Id() {
		
	}
	public FoodMap_Meal_Id(String shopName, String mealName) {
		this.shopName = shopName;
		this.mealName = mealName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shop_name) {
		this.shopName = shop_name;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String meal_name) {
		this.mealName = meal_name;
	}
	
}
