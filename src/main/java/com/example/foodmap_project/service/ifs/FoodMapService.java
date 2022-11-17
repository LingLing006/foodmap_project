package com.example.foodmap_project.service.ifs;

import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapResponse;

public interface FoodMapService {
// �Ыؤ@�ӷs���ө�
	public FoodMapResponse createShopInfo(String shopName, String city);

// �ק�ө���T
	public FoodMapResponse updateShopInfo(String shopName, String city, String newShopName);

// �Ыؤ@�ӷs���\�I
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel);

// �ק��\�I��T
	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel, String newMealName);

// �M��S�w���������ө�
	public FoodMapListResponse findShopByCity(String City, int displayAmount);

// �M��ө������X���P�H�W���ө�
	public FoodMapListResponse findShopByShopLevel(int shopLevel);

// �M��ө������X���P�H�W���ө��A�P������\�I�����X���P�H�W���\�I
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel);

}
