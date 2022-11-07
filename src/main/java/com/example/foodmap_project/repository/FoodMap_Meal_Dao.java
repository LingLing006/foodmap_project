package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;

@Repository
public interface FoodMap_Meal_Dao extends JpaRepository<FoodMap_Meal, FoodMap_Meal_Id>{

	public List<FoodMap_Meal> findByShopNameOrderByMealLevelDesc(String shopName);
//	public List<FoodMap_Meal> findBymealLevelGreaterThan(int mealLevel);
	public List<FoodMap_Meal> findByShopNameAllIgnoreCaseOrderByMealLevelDesc(String shopName);
	public List<FoodMap_Meal> findAllByOrderByMealLevelDesc();
}
