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
	private FoodMapMealDao mealDao;

	@Autowired
	private FoodMapShopDao shopDao;

	@Override
	public FoodMapResponse createShopInfo(String shopName, String city) {

		if (shopDao.existsById(shopName)) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_EXISTED.getMessage());
		} // shopName�w�s�b

		FoodMapShop shop = new FoodMapShop(shopName, city);
		shopDao.save(shop);
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel) {

		if (!shopDao.existsById(mealId.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		} // �䤣�즹shopName�A�����x�s���s�b���\�U���\�I
		if (mealDao.existsById(mealId)) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_EXISTED.getMessage());
		} // ��shopName & mealName�w�s�b

		FoodMapMeal meal = new FoodMapMeal(mealId.getShopName(), mealId.getMealName(), price, mealLevel);
		mealDao.save(meal);
		FoodMapResponse res = new FoodMapResponse(meal, FoodMapRtnCode.SUCCESSFUL.getMessage());

		List<FoodMapMeal> mealList = mealDao.findByShopName(mealId.getShopName());
//		int i = 0;
		int total = 0;
		for (FoodMapMeal mealItem : mealList) {
			total += mealItem.getMealLevel();
//			i++;
		}
		FoodMapShop shop = shopDao.getById(mealId.getShopName());
//		double shopLevel = (double) total / i;
//		shopLevel = ;
		shop.setShopLevel(Math.round(((double) total / mealList.size()) * 10.0) / 10.0);
		shopDao.save(shop);

		return res;
	}

	@Override
	public FoodMapResponse updateShopInfo(String shopName, String city, String newShopName) {
		FoodMapShop shop = shopDao.findById(shopName).get();
		if (shop == null) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		} // ���ө����s�b

		shopDao.delete(shop);
		if (StringUtils.hasText(city)) {
			shop.setCity(city);
		}
		if (StringUtils.hasText(newShopName)) {
			shop.setShopName(newShopName);
			List<FoodMapMeal> mealList = mealDao.findByShopNameOrderByMealLevelDesc(shopName);
			for (FoodMapMeal meal : mealList) {
				FoodMapMeal newMeal = meal;
				mealDao.delete(meal);
				newMeal.setShopName(newShopName);
				mealDao.save(newMeal);
			}
		}
		shopDao.save(shop);
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel , String newMealName) {

		FoodMapMeal meal = mealDao.findById(mealId).get();
		if (meal == null) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_INEXISTED.getMessage());
		} // ���ө��W��&�\�I�W�٤��s�b
		mealDao.delete(meal);
		
		if (price != 0) {
			meal.setPrice(price);
		}
		if (mealLevel != 0) {
			int total = 0;
			meal.setMealLevel(mealLevel);
			List<FoodMapMeal> mealList = mealDao.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(mealId.getShopName());
			for (FoodMapMeal mealitem : mealList) {
				total += mealitem.getMealLevel();
			}
			FoodMapShop shop = shopDao.getById(mealId.getShopName());			
			shop.setShopLevel(Math.round(((double) total / mealList.size()) * 10.0) / 10.0);
			shopDao.save(shop);
		}
		if(StringUtils.hasText(newMealName)) {
			meal.setMealName(newMealName);
		}
		mealDao.save(meal);
		return new FoodMapResponse(meal, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapMeal updateMealName(FoodMapMealId mealId, String newMealName) {

		Optional<FoodMapMeal> meal = mealDao.findById(mealId);

		mealDao.deleteById(mealId);
		meal.get().setMealName(newMealName);
		return mealDao.save(meal.get());

	}

	@Override
	public FoodMapListResponse findShopByCity(String City, int displayAmount) {

		if (shopDao.findByCityOrderByShopLevelDesc(City).isEmpty()) {
			return new FoodMapListResponse(FoodMapRtnCode.CITY_INEXISTED.getMessage());
		} // ���������s�b

		FoodMapListResponse foodMapListRes = new FoodMapListResponse();
		List<FoodMapResponse> resList = new ArrayList<>();
		foodMapListRes.setCity(City);

		List<FoodMapShop> shopList = shopDao.findByCityOrderByShopLevelDesc(City);
		if (displayAmount == 0 || displayAmount > shopList.size()) {
			displayAmount = shopList.size();
		}
		shopList = shopList.subList(0, displayAmount);
		// ���o�Ҧ��bCity�̪����a���(�Ω��a�Ʀ�Ƨ�),�b���o�����ܵ���

		for (FoodMapShop shop : shopList) {
			FoodMapResponse res = new FoodMapResponse();
			List<FoodMapMeal> MealList = mealDao.findByShopNameOrderByMealLevelDesc(shop.getShopName());
			for (FoodMapMeal meal : MealList) {
				meal.setShopName(null);
			}
			res.setMealList(MealList);
			res.setShopName(shop.getShopName());
			res.setShopLevel(shop.getShopLevel());
			resList.add(res);
		}
		foodMapListRes.setResList(resList);
		return foodMapListRes;
	}

	@Override
	public FoodMapListResponse findShopByShopLevel(int shopLevel) {

		FoodMapListResponse listRes = new FoodMapListResponse();
		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanOrderByShopLevelDesc((double)shopLevel);// �Ҧ��ө���ƥ�ShopLevel�Ƨ�
		List<FoodMapResponse> resList = new ArrayList<>();

		for (FoodMapShop shop : shopList) {
			
				FoodMapResponse res = new FoodMapResponse();
				List<FoodMapMeal> mealList = mealDao
						.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(shop.getShopName());// �����ŦX���󪺰ө����\�I
				res.setCity(shop.getCity());
				res.setShopName(shop.getShopName());
				res.setShopLevel(shop.getShopLevel());
				for (FoodMapMeal meal : mealList) {
					meal.setShopName(null);
				}
				res.setMealList(mealList);
				resList.add(res);
			
		}
		listRes.setResList(resList);
		return listRes;
	}

	@Override
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel) {

		FoodMapListResponse listRes = new FoodMapListResponse();
		List<FoodMapShop> shopList =  shopDao.findByShopLevelGreaterThanOrderByShopLevelDesc((double)shopLevel);// �Ҧ��ө����
//		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanOrderByShopLevelDesc(int shopLevel);// �Ҧ��ө����
		List<FoodMapResponse> resList = new ArrayList<>();

		for (FoodMapShop shop : shopList) {
			
				FoodMapResponse res = new FoodMapResponse();
				List<FoodMapMeal> mealList = mealDao
						.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(shop.getShopName());// �����ŦX���󪺰ө����\�I
				List<FoodMapMeal> newMealList = new ArrayList<FoodMapMeal>();
				for (FoodMapMeal meal : mealList) {
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


}
