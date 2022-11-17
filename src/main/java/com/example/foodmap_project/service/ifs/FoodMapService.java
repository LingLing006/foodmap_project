package com.example.foodmap_project.service.ifs;

import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapResponse;

public interface FoodMapService {
// 創建一個新的商店
	public FoodMapResponse createShopInfo(String shopName, String city);

// 修改商店資訊
	public FoodMapResponse updateShopInfo(String shopName, String city, String newShopName);

// 創建一個新的餐點
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel);

// 修改餐點資訊
	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel, String newMealName);

// 尋找特定城市中的商店
	public FoodMapListResponse findShopByCity(String City, int displayAmount);

// 尋找商店評價幾顆星以上的商店
	public FoodMapListResponse findShopByShopLevel(int shopLevel);

// 尋找商店評價幾顆星以上的商店，同時顯示餐點評價幾顆星以上的餐點
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel);

}
