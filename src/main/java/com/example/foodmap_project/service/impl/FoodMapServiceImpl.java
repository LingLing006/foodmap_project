package com.example.foodmap_project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.foodmap_project.constants.FoodMapRtnCode;
import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;
import com.example.foodmap_project.entity.FoodMapShop;
import com.example.foodmap_project.repository.FoodMapMealDao;
import com.example.foodmap_project.repository.FoodMapShopDao;
import com.example.foodmap_project.service.ifs.FoodMapService;
import com.example.foodmap_project.vo.FoodMapListResponse;
import com.example.foodmap_project.vo.FoodMapResponse;

@Service
public class FoodMapServiceImpl implements FoodMapService {

	@Autowired
	private FoodMapMealDao mealDao;// 連接餐點的資料庫

	@Autowired
	private FoodMapShopDao shopDao;// 連接商店的資料庫

	@Override // 創造一個新的商店資訊
	public FoodMapResponse createShopInfo(String shopName, String city) {

		if (shopDao.existsById(shopName)) {// 判斷商店名稱是否已存在
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_EXISTED.getMessage());
		} // shopName已存在

		FoodMapShop shop = new FoodMapShop(shopName, city);// 創建一個新的shop
		shopDao.save(shop);// 儲存回去資料庫
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapResponse updateShopInfo(String shopName, String city, String newShopName) {

		Optional<FoodMapShop> shopOp = shopDao.findById(shopName);
		// 判斷此商店是否存在
		if (shopOp.orElse(null) == null) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		}

		FoodMapShop shop = shopOp.get();
		shopDao.delete(shop);// 將原先的資料刪掉

		// 如果有更改城市，進行設定
		if (StringUtils.hasText(city)) {
			shop.setCity(city);
		}

		// 如果有更改商店名稱，進行設定
		if (StringUtils.hasText(newShopName)) {
			// 更改商店資料庫裡的商店名稱
			shop.setShopName(newShopName);

			// 更改餐點資料庫裡，所有此餐廳餐點的商店名稱
			List<FoodMapMeal> mealList = mealDao.findByShopNameOrderByMealLevelDesc(shopName);

			for (FoodMapMeal meal : mealList) {
				mealDao.delete(meal);
				meal.setShopName(newShopName);
				mealDao.save(meal);
			}

		}
		shopDao.save(shop);
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override // 創造一個新的餐點資訊
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel) {

		// 判斷商店的資料庫中，使否存在此餐廳名稱
		if (!shopDao.existsById(mealId.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		}

		// 判斷此商店名稱及餐點名稱是否已存在
		if (mealDao.existsById(mealId)) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_EXISTED.getMessage());
		}

		// 建立新的餐點存進資料庫
		FoodMapMeal meal = new FoodMapMeal(mealId.getShopName(), mealId.getMealName(), price, mealLevel);
		mealDao.save(meal);

		// 創建新餐點後，更新店家評價。
		List<FoodMapMeal> mealList = mealDao.findByShopName(mealId.getShopName());

		int total = 0;
		for (FoodMapMeal mealItem : mealList) {
			total += mealItem.getMealLevel();
		}

		// 將此店家的所有餐點評價平均，即為店家評價
		FoodMapShop shop = shopDao.getById(mealId.getShopName());
		shop.setShopLevel(Math.round(((double) total / mealList.size()) * 10.0) / 10.0);
		shopDao.save(shop);

		// 把創好的新餐點回傳
		FoodMapResponse res = new FoodMapResponse(meal, FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}

	@Override
	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel, String newMealName) {

		// 判斷此商店名稱及餐點名稱是否存在
		Optional<FoodMapMeal> mealOp = mealDao.findById(mealId);
		if (mealOp.orElse(null) == null) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_INEXISTED.getMessage());
		}

		// 刪除原先的資訊
		FoodMapMeal meal = mealOp.get();
		mealDao.delete(meal);

		// 如果有更改餐點名稱，進行設定
		if (StringUtils.hasText(newMealName)) {
			meal.setMealName(newMealName);
		}

		// 如果有更改餐點價格，進行設定
		if (price != 0) {
			meal.setPrice(price);
		}

		// 如果有修改餐點評價，進行設定後，店家評價也需要進行更改
		if (mealLevel != 0) {

			// 設定新的餐點評價
			meal.setMealLevel(mealLevel);

			// 計算此商店的所有餐點評價的加總
			double total = 0;
			List<FoodMapMeal> mealList = mealDao.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(mealId.getShopName());
			for (FoodMapMeal mealitem : mealList) {
				total += mealitem.getMealLevel();
			}

			// 計算此商店的所有餐點評價的平均，並將此平均設定為店家評價
			FoodMapShop shop = shopDao.getById(mealId.getShopName());
			shop.setShopLevel(Math.round(((total + mealLevel) / (mealList.size() + 1)) * 10.0) / 10.0);
			shopDao.save(shop);
		}

		mealDao.save(meal);
		return new FoodMapResponse(meal, FoodMapRtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapListResponse findShopByCity(String City, int displayAmount) {

		// 判斷此城市是否存在
		if (shopDao.findByCityOrderByShopLevelDesc(City).isEmpty()) {
			return new FoodMapListResponse(FoodMapRtnCode.CITY_INEXISTED.getMessage());
		}

		// 建立回傳的類別，並將想搜尋的城市設定進去
		FoodMapListResponse resListObject = new FoodMapListResponse();
		resListObject.setCity(City);

		// 建立存放多筆店家資訊及餐點資訊的List
		List<FoodMapResponse> resList = new ArrayList<>();

		// 取得所有在此城市裡的店家資料(用店家排行排序),在取得限制的顯示筆數
		List<FoodMapShop> shopList = shopDao.findByCityOrderByShopLevelDesc(City);

		// 當顯示筆數為0，或是大於在此城市所有的店家數，則將所有資訊顯示
		if (displayAmount == 0 || displayAmount > shopList.size()) {
			displayAmount = shopList.size();
		}

		// 取得用戶所要求的顯示筆數長度的List
		shopList = shopList.subList(0, displayAmount);

		// 建立一個用來存放shopList中，所有店家名稱的List
		List<String> shopNameList = new ArrayList<>();

		// 並取得shopList中所有的店家名稱後，存放進shopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// 找出所有在shopNameList中，各間店家的所有餐點
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao.findByShopNameIn(shopNameList);

		// 一一找出店家以及將此店家的所有餐點，存放進一個mealList，並將其加入回傳的類別當中
		for (FoodMapShop shop : shopList) {

			// 創建一個List用來存放此店家的所有餐點
			List<FoodMapMeal> mealList = new ArrayList<>();

			// 挑選出當前外層迴圈的店家的所有餐點，並將餐點存入mealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}

			// 建立用於存放一個店家資訊及餐點資訊的物件，並將此物件一一加入可以回傳多筆店家資訊的List
			FoodMapResponse res = new FoodMapResponse(mealList, shop.getShopName(), shop.getShopLevel());
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());

		return resListObject;
	}

	@Override
	public FoodMapListResponse findShopByShopLevel(int shopLevel) {

		// 建立回傳的類別
		FoodMapListResponse resListObject = new FoodMapListResponse();

		// 建立存放多筆店家資訊及餐點資訊的List
		List<FoodMapResponse> resList = new ArrayList<>();

		// 搜尋資料庫中，所有商店評價為幾顆星(含)以上的店家
		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanEqualOrderByShopLevelDesc((double) shopLevel);

		// 建立一個用來存放shopList中，所有店家名稱的List
		List<String> shopNameList = new ArrayList<>();

		// 並取得shopList中所有的店家名稱後，存放進shopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// 找出所有在shopNameList中，各間店家的所有餐點
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao.findByShopNameIn(shopNameList);

		// 一一找出店家以及將此店家的所有餐點，存放進一個mealList，並將其加入回傳的類別當中
		for (FoodMapShop shop : shopList) {

			// 創建一個List用來存放此店家的所有餐點
			List<FoodMapMeal> mealList = new ArrayList<>();

			// 挑選出當前外層迴圈的店家的所有餐點，並將餐點存入mealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}

			// 建立用於存放一個店家資訊及餐點資訊的物件，並將此物件一一加入可以回傳多筆店家資訊的List
			FoodMapResponse res = new FoodMapResponse(mealList, shop);
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());

		return resListObject;
	}

	@Override
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel) {

		// 建立回傳的類別
		FoodMapListResponse resListObject = new FoodMapListResponse();
		
		// 建立存放多筆店家資訊及餐點資訊的List
		List<FoodMapResponse> resList = new ArrayList<>();
		
		// 搜尋資料庫中，所有商店評價為幾顆星(含)以上的店家
		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanEqualOrderByShopLevelDesc((double) shopLevel);// 所有商店資料

		// 建立一個用來存放shopList中，所有店家名稱的List
		List<String> shopNameList = new ArrayList<>();

		// 並取得shopList中所有的店家名稱後，存放進shopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// 找出所有在shopNameList中，各間店家餐點評價己興(含)以上的餐點
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao
				.findByShopNameInAndMealLevelGreaterThanEqualOrderByMealLevelDesc(shopNameList, mealLevel);

		// 一一找出店家以及將此店家的所有餐點，存放進一個mealList，並將其加入回傳的類別當中
		for (FoodMapShop shop : shopList) {

			// 創建一個List用來存放此店家餐點評價己興(含)以上的餐點
			List<FoodMapMeal> mealList = new ArrayList<>();

			// 挑選出當前外層迴圈的店家的所有餐點，並將餐點存入mealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}
			
			// 建立用於存放一個店家資訊及餐點資訊的物件，並將此物件一一加入可以回傳多筆店家資訊的List
			FoodMapResponse res = new FoodMapResponse(mealList, shop);
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		
		return resListObject;
	}

}
