package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMap_Shop;

@Repository
public interface FoodMap_Shop_Dao extends JpaRepository<FoodMap_Shop, String> {

	public List<FoodMap_Shop> findByCityOrderByShopLevelDesc(String city);
	public List<FoodMap_Shop> findTop3ByCityOrderByShopLevelDesc(String city);
	public List<FoodMap_Shop> findByShopNameAllIgnoreCase(String shopName);
	public List<FoodMap_Shop> findAllByOrderByShopLevelDesc();
	
	
//	public List<FoodMap_Shop> findAllOrderByShopLevelDesc();
}
