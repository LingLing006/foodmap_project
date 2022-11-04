package com.example.foodmap_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMap_Shop;

@Repository
public interface FoodMap_Shop_Dao extends JpaRepository<FoodMap_Shop, String> {

}
