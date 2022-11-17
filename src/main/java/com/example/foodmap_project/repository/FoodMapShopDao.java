package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapShop;

@Repository
public interface FoodMapShopDao extends JpaRepository<FoodMapShop, String> {

	// 使用城市搜尋店家，並用店家評價遞減排序
	public List<FoodMapShop> findByCityOrderByShopLevelDesc(String city);

	// 使用店家評價搜尋評價高於幾顆星(含)以上的店家，並用店家評價遞減排序
	public List<FoodMapShop> findByShopLevelGreaterThanEqualOrderByShopLevelDesc(double shopLevel);
}
