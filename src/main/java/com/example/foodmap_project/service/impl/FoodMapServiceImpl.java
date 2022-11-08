package com.example.foodmap_project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.foodmap_project.entity.FoodMap_Meal;
import com.example.foodmap_project.entity.FoodMap_Meal_Id;
import com.example.foodmap_project.entity.FoodMap_Shop;
import com.example.foodmap_project.repository.FoodMap_Meal_Dao;
import com.example.foodmap_project.repository.FoodMap_Shop_Dao;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;
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

//		if (!shopDao.existsById(mealId.getShopName())) {
//			return new FoodMap_Meal(); //商店表裡面沒有此商店名稱
//		}
		if (mealDao.existsById(mealId)) {
			return null; // 餐點表裡面已經存在此店家名稱&餐點
		}

		FoodMap_Meal meal = new FoodMap_Meal(mealId.getShopName(), mealId.getMealName(), price, mealLevel);
		mealDao.save(meal);

		List<FoodMap_Meal> mealList = mealDao.findAll();
		int i = 0;
		int total = 0;
		for (FoodMap_Meal mealItem : mealList) {
			if (mealItem.getShopName().equalsIgnoreCase(mealId.getShopName())) {
				total += mealItem.getMealLevel();
				i++;
			}
		}
		FoodMap_Shop shop = shopDao.getById(mealId.getShopName());
		double shopLevel = (double) total / i;
		shopLevel = Math.round(shopLevel * 10.0) / 10.0;
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

		List<FoodMap_Meal> mealList = mealDao.findByShopNameOrderByMealLevelDesc(shopName);
		for (FoodMap_Meal meal : mealList) {
			FoodMap_Meal meal1 = meal;
			mealDao.delete(meal);
			meal1.setShopName(newShopName);
			mealDao.save(meal1);
		}

		return shopDao.save(shop);

	}

	@Override
	public FoodMap_Meal updateMealInfo(FoodMap_Meal_Id mealId, int price, int mealLevel) {

		FoodMap_Meal meal = mealDao.getById(mealId);
		if (price != 0) {
			meal.setPrice(price);
		}
		if (mealLevel != 0) {
			int i = 0;
			int total = 0;
			meal.setMealLevel(mealLevel);
			List<FoodMap_Meal> mealList = mealDao.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(mealId.getShopName());
			for (FoodMap_Meal mealitem : mealList) {
				total += mealitem.getMealLevel();
				i++;
			}
			double shopLevel = (double) total / i;
			shopLevel = Math.round(shopLevel * 10.0) / 10.0;
			FoodMap_Shop shop = shopDao.getById(mealId.getShopName());
			shop.setShopLevel(shopLevel);
			shopDao.save(shop);
		}
		return mealDao.save(meal);
	}

	@Override
	public FoodMap_Meal updateMealName(FoodMap_Meal_Id mealId, String newMealName) {

		Optional<FoodMap_Meal> meal = mealDao.findById(mealId);

		mealDao.deleteById(mealId);
		meal.get().setMealName(newMealName);
		return mealDao.save(meal.get());

	}

	@Override
	public FoodMapListResponse findShopByCity(String City, int displayAmount) {

		FoodMapListResponse listRes = new FoodMapListResponse();
		listRes.setCity(City);

		List<FoodMap_Shop> shopList = shopDao.findByCityOrderByShopLevelDesc(City);
		List<FoodMap_Meal> allMealList = mealDao.findAllByOrderByMealLevelDesc();
		List<FoodMapResponse> resList = new ArrayList<>();

		if (displayAmount == 0 || displayAmount > shopList.size()) {
			displayAmount = shopList.size();
		}

		for (int i = 0; i < displayAmount; i++) {
			FoodMapResponse res = new FoodMapResponse();
			List<FoodMap_Meal> mealList = new ArrayList<>();
			for (FoodMap_Meal meal : allMealList) {

				if (shopList.get(i).getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}
			res.setShopName(shopList.get(i).getShopName());
			res.setShopLevel(shopList.get(i).getShopLevel());
			res.setMealList(mealList);
			resList.add(res);
		}

		listRes.setResList(resList);
		return listRes;
	}

	@Override
	public FoodMapListResponse findShopByShopLevel(int shopLevel) {

		FoodMapListResponse listRes = new FoodMapListResponse();
		List<FoodMap_Shop> shopList = shopDao.findAllByOrderByShopLevelDesc();// 所有商店資料用ShopLevel排序
		List<FoodMapResponse> resList = new ArrayList<>();

		for (FoodMap_Shop shop : shopList) {
			if (shop.getShopLevel() >= shopLevel) {
				FoodMapResponse res = new FoodMapResponse();
				List<FoodMap_Meal> mealList = mealDao
						.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(shop.getShopName());// 評價符合條件的商店的餐點
				res.setCity(shop.getCity());
				res.setShopName(shop.getShopName());
				res.setShopLevel(shop.getShopLevel());
				for (FoodMap_Meal meal : mealList) {
					meal.setShopName(null);
				}
				res.setMealList(mealList);
				resList.add(res);
			}
		}
		listRes.setResList(resList);
		return listRes;
	}
//		for (int i = 0; i < shopList.size(); i++) {
//			for (int j = i + 1; j < shopList.size(); j++) {
//				if (shopList.get(i).getShopLevel() < shopList.get(j).getShopLevel()) {
//					FoodMap_Shop shop = shopList.get(j);
//					shopList.add(j, shopList.get(i));
//					shopList.add(i, shop);
//				}
//			}
//		}

	@Override
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel) {

		FoodMapListResponse listRes = new FoodMapListResponse();
		List<FoodMap_Shop> shopList = shopDao.findAllByOrderByShopLevelDesc();// 所有商店資料
		List<FoodMapResponse> resList = new ArrayList<>();

		for (FoodMap_Shop shop : shopList) {
			if (shop.getShopLevel() >= shopLevel) {
				FoodMapResponse res = new FoodMapResponse();
				List<FoodMap_Meal> mealList = mealDao
						.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(shop.getShopName());// 評價符合條件的商店的餐點
				List<FoodMap_Meal> newMealList = new ArrayList<FoodMap_Meal>();
				for (FoodMap_Meal meal : mealList) {
					if (meal.getMealLevel() >= mealLevel) {
						meal.setShopName(null);
						newMealList.add(meal);
					}
				}
				res.setCity(shop.getCity());
				res.setShopName(shop.getShopName());
				res.setShopLevel(shop.getShopLevel());
				res.setMealList(newMealList);
				resList.add(res);
			}
		}

		listRes.setResList(resList);
		return listRes;

	}

//	@Override
//    public List<FoodMap_Shop> findAll() {
//        return shopDao.findAll(orderByIdAsc());
//    }
//    private Sort orderByIdAsc() {
//    return new Sort(Sort.Direction.ASC, "shopLevel");
//    }

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
