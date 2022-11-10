package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapShop;

@Repository
public interface FoodMapShopDao extends JpaRepository<FoodMapShop, String> {

	public List<FoodMapShop> findByCityOrderByShopLevelDesc(String city);
	public List<FoodMapShop> findTop3ByCityOrderByShopLevelDesc(String city);
	public List<FoodMapShop> findByShopNameAllIgnoreCase(String shopName);
	public List<FoodMapShop> findAllByOrderByShopLevelDesc();
	
	public List<FoodMapShop> findByShopLevelGreaterThanOrderByShopLevelDesc(double shopLevel);
//	public List<FoodMap_Shop> findAllOrderByShopLevelDesc();
}
