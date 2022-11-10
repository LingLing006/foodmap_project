package com.example.foodmap_project.service.ifs;

import java.util.List;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.entity.FoodMapShop;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapResponse;

public interface FoodMapService {
	public FoodMapResponse createShopInfo(String shopName, String city);
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel);

	public FoodMapResponse updateShopInfo(String shopName, String city ,String newShopName);

	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel , String newMealName);
	public FoodMapMeal updateMealName(FoodMapMealId mealId, String newMealName);

	public FoodMapListResponse findShopByCity(String City,int displayAmount);
	public FoodMapListResponse findShopByShopLevel(int shopLevel);
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel,int mealLevel);
	


//	public List<FoodMap_Shop> findAll();

}
