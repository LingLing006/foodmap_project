package com.example.foodmap_project.service.ifs;

import java.util.List;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.example.foodmap_project.vo.FoodMapResponse;

public interface FoodMapService {
	public FoodMap_Shop createShopInfo(String shopName, String city);
	public FoodMap_Meal createMealInfo(FoodMap_Meal_Id mealId, int price, int mealLevel);

	public FoodMap_Shop updateShopInfo(String shopName, String city);
	public FoodMap_Shop updateShopName(String shopName, String newShopName);

	public FoodMap_Meal updateMealInfo(FoodMap_Meal_Id mealId, int price, int mealLevel);
	public FoodMap_Meal updateMealId(FoodMap_Meal_Id mealId, FoodMap_Meal_Id newMealId);

	public FoodMapResponse findShopByCity(String City,int displayAmount);
	public FoodMapResponse findShopByShopLevel(int shopLevel);
	public FoodMapResponse findShopByShopLevelAndMealLevel(int shopLevel,int mealLevel);
	
	public FoodMap_Shop findShopById(String shopName);
	public FoodMap_Meal findMealById(FoodMap_Meal_Id mealId);

}
