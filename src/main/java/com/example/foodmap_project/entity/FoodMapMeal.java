package com.example.foodmap_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//import org.springframework.data.annotation.Id;

@Entity
@Table(name = "foodmap_meal")
@IdClass(FoodMapMealId.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FoodMapMeal {

	@Id
	@Column(name = "shop_name")
	private String shopName;

	@Id
	@Column(name = "meal_name")
	private String mealName;

	@Column(name = "price")
	private int price;

	@JsonProperty("meal_level")
	@Column(name = "meal_level")
	private int mealLevel;

//=====================================	
	
	public FoodMapMeal() {

	}

	public FoodMapMeal(String shop_name, String meal_name, int price, int mealLevel) {
		this.shopName = shop_name;
		this.mealName = meal_name;
		this.mealLevel = mealLevel;
		this.price = price;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMealLevel() {
		return mealLevel;
	}

	public void setMealLevel(int meal_level) {
		this.mealLevel = meal_level;
	}

}
