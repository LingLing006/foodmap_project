package com.example.foodmap_project.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FoodMapMealId implements Serializable {

	private String shopName;

	private String mealName;

//======================================

	public FoodMapMealId() {

	}

	public FoodMapMealId(String shopName, String mealName) {
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
