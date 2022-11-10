package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.entity.FoodMapShop;

@Repository
public interface FoodMapMealDao extends JpaRepository<FoodMapMeal, FoodMapMealId>{

	public List<FoodMapMeal> findByShopNameOrderByMealLevelDesc(String shopName);
	public List<FoodMapMeal> findByMealLevelGreaterThan(int mealLevel);
	public List<FoodMapMeal> findByShopNameAllIgnoreCaseOrderByMealLevelDesc(String shopName);
	public List<FoodMapMeal> findAllByOrderByMealLevelDesc();
	public List<FoodMapMeal> findByShopName(String shopName);

	
	public List<FoodMapMeal> findByShopNameAllIgnoreCaseAndMealLevelGreaterThanOrderByMealLevelDesc(String shopName,int mealLevel);

}
