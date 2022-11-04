package com.example.foodmap_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;

@Repository
public interface FoodMap_Meal_Dao extends JpaRepository<FoodMap_Meal, FoodMap_Meal_Id>{

}
