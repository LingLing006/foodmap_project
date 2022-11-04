package com.example.foodmap_project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.example.foodmap_project.repository.FoodMap_Meal_Dao;
import com.example.foodmap_project.repository.FoodMap_Shop_Dao;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapResponse;

@Service
public class FoodMapServiceImpl implements FoodMapService {

	@Autowired
	private FoodMap_Meal_Dao mealDao;

	@Autowired
	private FoodMap_Shop_Dao shopDao;

	@Override
	public FoodMap_Shop createShopInfo(String shopName, String city) {

		if (shopDao.existsById(shopName)) {
			return null;
		}

		FoodMap_Shop shop = new FoodMap_Shop(shopName, city);
		shopDao.save(shop);
		return shop;
	}

	@Override
	public FoodMap_Meal createMealInfo(FoodMap_Meal_Id mealId, int price, int mealLevel) {

		if (mealDao.existsById(mealId)) {
			return null;
		}
		FoodMap_Meal meal = new FoodMap_Meal(mealId.getShopName(), mealId.getMealName(), price, mealLevel);
		mealDao.save(meal);
		
		List<FoodMap_Meal> mealList = mealDao.findAll();
		int i = 0 ;
		int total = 0;
		for(FoodMap_Meal mealItem : mealList) {
			if(mealItem.getShopName().equalsIgnoreCase(mealId.getShopName())) {
				total += mealItem.getMealLevel();
				i++;
			}
		}
		double shopLevel = (double) total/i;
		shopLevel = Math.round(shopLevel*10.0)/10.0;
		FoodMap_Shop shop = shopDao.getById(mealId.getShopName());
		shop.setShopLevel(shopLevel);
		shopDao.save(shop);
		
		
		return meal;
	}

	@Override
	public FoodMap_Shop updateShopInfo(String shopName, String city) {
//		if (shopDao.existsById(shopName)) {
		FoodMap_Shop shop = shopDao.findById(shopName).get();
		shop.setCity(city);
		return shopDao.save(shop);
//		}
//		return null;
	}

	@Override
	public FoodMap_Shop updateShopName(String shopName, String newShopName) {

		FoodMap_Shop shop = shopDao.findById(shopName).get();
		shopDao.delete(shop);
		shop.setShopName(newShopName);
		return shopDao.save(shop);

	}

	@Override
	public FoodMap_Meal updateMealInfo(FoodMap_Meal_Id mealId, int price, int mealLevel) {

		FoodMap_Meal meal = mealDao.getById(mealId);
		if (price != 0) {
			meal.setPrice(price);
		} 
		if (mealLevel != 0) {
			meal.setMealLevel(mealLevel);
		}
		return mealDao.save(meal);
	}

	@Override
	public FoodMap_Meal updateMealId(FoodMap_Meal_Id mealId, FoodMap_Meal_Id newMealId) {
		
		FoodMap_Meal meal = mealDao.getById(mealId);
		mealDao.delete(meal);
		meal.setShopName(newMealId.getShopName());
		meal.setMealName(newMealId.getMealName());
		return mealDao.save(meal);
		
	}

	@Override
	public FoodMapResponse findShopByCity(String City, int displayAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodMapResponse findShopByShopLevel(int shopLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodMapResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel) {
		// TODO Auto-generated method stub
		return null;
	}

//==============================================================
	@Override
	public FoodMap_Shop findShopById(String shopName) {

		if (shopDao.existsById(shopName)) {
			return shopDao.getById(shopName);
		}
		return null;
	}

	@Override
	public FoodMap_Meal findMealById(FoodMap_Meal_Id mealId) {

		if (mealDao.existsById(mealId)) {
			return mealDao.getById(mealId);
		}
		return null;
	}

}
