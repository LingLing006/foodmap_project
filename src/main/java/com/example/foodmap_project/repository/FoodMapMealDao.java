package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;

@Repository
public interface FoodMapMealDao extends JpaRepository<FoodMapMeal, FoodMapMealId> {

	// 使用店家名稱搜尋餐點
	public List<FoodMapMeal> findByShopName(String shopName);

	// 使用店家名稱搜尋餐點
	public List<FoodMapMeal> findByShopNameOrderByMealLevelDesc(String shopName);

	// 使用店家名稱(英文不分大小寫)搜尋餐點，並用餐點評價遞減排序
	public List<FoodMapMeal> findByShopNameAllIgnoreCaseOrderByMealLevelDesc(String shopName);

	// 使用存有店家名稱的List搜尋，此List中所有店家的餐點
	public List<FoodMapMeal> findByShopNameIn(List<String> shopNameList);

	// 使用存有店家名稱的List以及餐點評價幾星(含)以上搜尋，此List中所有店家，餐點評價幾星(含)以上的餐點
	public List<FoodMapMeal> findByShopNameInAndMealLevelGreaterThanEqualOrderByMealLevelDesc(List<String> shopNameList,
			int mealLevel);

}
