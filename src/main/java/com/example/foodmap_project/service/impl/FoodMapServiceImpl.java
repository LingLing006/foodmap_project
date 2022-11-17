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
	private FoodMapMealDao mealDao;// �s���\�I����Ʈw

	@Autowired
	private FoodMapShopDao shopDao;// �s���ө�����Ʈw

	@Override // �гy�@�ӷs���ө���T
	public FoodMapResponse createShopInfo(String shopName, String city) {

		if (shopDao.existsById(shopName)) {// �P�_�ө��W�٬O�_�w�s�b
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_EXISTED.getMessage());
		} // shopName�w�s�b

		FoodMapShop shop = new FoodMapShop(shopName, city);// �Ыؤ@�ӷs��shop
		shopDao.save(shop);// �x�s�^�h��Ʈw
		return new FoodMapResponse(shop, FoodMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapResponse updateShopInfo(String shopName, String city, String newShopName) {

		Optional<FoodMapShop> shopOp = shopDao.findById(shopName);
		// �P�_���ө��O�_�s�b
		if (shopOp.orElse(null) == null) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		}

		FoodMapShop shop = shopOp.get();
		shopDao.delete(shop);// �N�������ƧR��

		// �p�G����﫰���A�i��]�w
		if (StringUtils.hasText(city)) {
			shop.setCity(city);
		}

		// �p�G�����ө��W�١A�i��]�w
		if (StringUtils.hasText(newShopName)) {
			// ���ө���Ʈw�̪��ө��W��
			shop.setShopName(newShopName);

			// ����\�I��Ʈw�̡A�Ҧ����\�U�\�I���ө��W��
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

	@Override // �гy�@�ӷs���\�I��T
	public FoodMapResponse createMealInfo(FoodMapMealId mealId, int price, int mealLevel) {

		// �P�_�ө�����Ʈw���A�ϧ_�s�b���\�U�W��
		if (!shopDao.existsById(mealId.getShopName())) {
			return new FoodMapResponse(FoodMapRtnCode.SHOPNAME_INEXISTED.getMessage());
		}

		// �P�_���ө��W�٤��\�I�W�٬O�_�w�s�b
		if (mealDao.existsById(mealId)) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_EXISTED.getMessage());
		}

		// �إ߷s���\�I�s�i��Ʈw
		FoodMapMeal meal = new FoodMapMeal(mealId.getShopName(), mealId.getMealName(), price, mealLevel);
		mealDao.save(meal);

		// �Ыطs�\�I��A��s���a�����C
		List<FoodMapMeal> mealList = mealDao.findByShopName(mealId.getShopName());

		int total = 0;
		for (FoodMapMeal mealItem : mealList) {
			total += mealItem.getMealLevel();
		}

		// �N�����a���Ҧ��\�I���������A�Y�����a����
		FoodMapShop shop = shopDao.getById(mealId.getShopName());
		shop.setShopLevel(Math.round(((double) total / mealList.size()) * 10.0) / 10.0);
		shopDao.save(shop);

		// ��Цn���s�\�I�^��
		FoodMapResponse res = new FoodMapResponse(meal, FoodMapRtnCode.SUCCESSFUL.getMessage());
		return res;
	}

	@Override
	public FoodMapResponse updateMealInfo(FoodMapMealId mealId, int price, int mealLevel, String newMealName) {

		// �P�_���ө��W�٤��\�I�W�٬O�_�s�b
		Optional<FoodMapMeal> mealOp = mealDao.findById(mealId);
		if (mealOp.orElse(null) == null) {
			return new FoodMapResponse(FoodMapRtnCode.MEALNAME_INEXISTED.getMessage());
		}

		// �R���������T
		FoodMapMeal meal = mealOp.get();
		mealDao.delete(meal);

		// �p�G������\�I�W�١A�i��]�w
		if (StringUtils.hasText(newMealName)) {
			meal.setMealName(newMealName);
		}

		// �p�G������\�I����A�i��]�w
		if (price != 0) {
			meal.setPrice(price);
		}

		// �p�G���ק��\�I�����A�i��]�w��A���a�����]�ݭn�i����
		if (mealLevel != 0) {

			// �]�w�s���\�I����
			meal.setMealLevel(mealLevel);

			// �p�⦹�ө����Ҧ��\�I�������[�`
			double total = 0;
			List<FoodMapMeal> mealList = mealDao.findByShopNameAllIgnoreCaseOrderByMealLevelDesc(mealId.getShopName());
			for (FoodMapMeal mealitem : mealList) {
				total += mealitem.getMealLevel();
			}

			// �p�⦹�ө����Ҧ��\�I�����������A�ñN�������]�w�����a����
			FoodMapShop shop = shopDao.getById(mealId.getShopName());
			shop.setShopLevel(Math.round(((total + mealLevel) / (mealList.size() + 1)) * 10.0) / 10.0);
			shopDao.save(shop);
		}

		mealDao.save(meal);
		return new FoodMapResponse(meal, FoodMapRtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	@Override
	public FoodMapListResponse findShopByCity(String City, int displayAmount) {

		// �P�_�������O�_�s�b
		if (shopDao.findByCityOrderByShopLevelDesc(City).isEmpty()) {
			return new FoodMapListResponse(FoodMapRtnCode.CITY_INEXISTED.getMessage());
		}

		// �إߦ^�Ǫ����O�A�ñN�Q�j�M�������]�w�i�h
		FoodMapListResponse resListObject = new FoodMapListResponse();
		resListObject.setCity(City);

		// �إߦs��h�����a��T���\�I��T��List
		List<FoodMapResponse> resList = new ArrayList<>();

		// ���o�Ҧ��b�������̪����a���(�Ω��a�Ʀ�Ƨ�),�b���o�����ܵ���
		List<FoodMapShop> shopList = shopDao.findByCityOrderByShopLevelDesc(City);

		// ����ܵ��Ƭ�0�A�άO�j��b�������Ҧ������a�ơA�h�N�Ҧ���T���
		if (displayAmount == 0 || displayAmount > shopList.size()) {
			displayAmount = shopList.size();
		}

		// ���o�Τ�ҭn�D����ܵ��ƪ��ת�List
		shopList = shopList.subList(0, displayAmount);

		// �إߤ@�ӥΨӦs��shopList���A�Ҧ����a�W�٪�List
		List<String> shopNameList = new ArrayList<>();

		// �è��oshopList���Ҧ������a�W�٫�A�s��ishopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// ��X�Ҧ��bshopNameList���A�U�����a���Ҧ��\�I
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao.findByShopNameIn(shopNameList);

		// �@�@��X���a�H�αN�����a���Ҧ��\�I�A�s��i�@��mealList�A�ñN��[�J�^�Ǫ����O��
		for (FoodMapShop shop : shopList) {

			// �Ыؤ@��List�ΨӦs�񦹩��a���Ҧ��\�I
			List<FoodMapMeal> mealList = new ArrayList<>();

			// �D��X��e�~�h�j�骺���a���Ҧ��\�I�A�ñN�\�I�s�JmealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}

			// �إߥΩ�s��@�ө��a��T���\�I��T������A�ñN������@�@�[�J�i�H�^�Ǧh�����a��T��List
			FoodMapResponse res = new FoodMapResponse(mealList, shop.getShopName(), shop.getShopLevel());
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());

		return resListObject;
	}

	@Override
	public FoodMapListResponse findShopByShopLevel(int shopLevel) {

		// �إߦ^�Ǫ����O
		FoodMapListResponse resListObject = new FoodMapListResponse();

		// �إߦs��h�����a��T���\�I��T��List
		List<FoodMapResponse> resList = new ArrayList<>();

		// �j�M��Ʈw���A�Ҧ��ө��������X���P(�t)�H�W�����a
		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanEqualOrderByShopLevelDesc((double) shopLevel);

		// �إߤ@�ӥΨӦs��shopList���A�Ҧ����a�W�٪�List
		List<String> shopNameList = new ArrayList<>();

		// �è��oshopList���Ҧ������a�W�٫�A�s��ishopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// ��X�Ҧ��bshopNameList���A�U�����a���Ҧ��\�I
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao.findByShopNameIn(shopNameList);

		// �@�@��X���a�H�αN�����a���Ҧ��\�I�A�s��i�@��mealList�A�ñN��[�J�^�Ǫ����O��
		for (FoodMapShop shop : shopList) {

			// �Ыؤ@��List�ΨӦs�񦹩��a���Ҧ��\�I
			List<FoodMapMeal> mealList = new ArrayList<>();

			// �D��X��e�~�h�j�骺���a���Ҧ��\�I�A�ñN�\�I�s�JmealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}

			// �إߥΩ�s��@�ө��a��T���\�I��T������A�ñN������@�@�[�J�i�H�^�Ǧh�����a��T��List
			FoodMapResponse res = new FoodMapResponse(mealList, shop);
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());

		return resListObject;
	}

	@Override
	public FoodMapListResponse findShopByShopLevelAndMealLevel(int shopLevel, int mealLevel) {

		// �إߦ^�Ǫ����O
		FoodMapListResponse resListObject = new FoodMapListResponse();
		
		// �إߦs��h�����a��T���\�I��T��List
		List<FoodMapResponse> resList = new ArrayList<>();
		
		// �j�M��Ʈw���A�Ҧ��ө��������X���P(�t)�H�W�����a
		List<FoodMapShop> shopList = shopDao.findByShopLevelGreaterThanEqualOrderByShopLevelDesc((double) shopLevel);// �Ҧ��ө����

		// �إߤ@�ӥΨӦs��shopList���A�Ҧ����a�W�٪�List
		List<String> shopNameList = new ArrayList<>();

		// �è��oshopList���Ҧ������a�W�٫�A�s��ishopNameList
		for (FoodMapShop shop : shopList) {
			shopNameList.add(shop.getShopName());
		}

		// ��X�Ҧ��bshopNameList���A�U�����a�\�I�����v��(�t)�H�W���\�I
		List<FoodMapMeal> mealListFindByShopNameIn = mealDao
				.findByShopNameInAndMealLevelGreaterThanEqualOrderByMealLevelDesc(shopNameList, mealLevel);

		// �@�@��X���a�H�αN�����a���Ҧ��\�I�A�s��i�@��mealList�A�ñN��[�J�^�Ǫ����O��
		for (FoodMapShop shop : shopList) {

			// �Ыؤ@��List�ΨӦs�񦹩��a�\�I�����v��(�t)�H�W���\�I
			List<FoodMapMeal> mealList = new ArrayList<>();

			// �D��X��e�~�h�j�骺���a���Ҧ��\�I�A�ñN�\�I�s�JmealList
			for (FoodMapMeal meal : mealListFindByShopNameIn) {
				if (shop.getShopName().equalsIgnoreCase(meal.getShopName())) {
					meal.setShopName(null);
					mealList.add(meal);
				}
			}
			
			// �إߥΩ�s��@�ө��a��T���\�I��T������A�ñN������@�@�[�J�i�H�^�Ǧh�����a��T��List
			FoodMapResponse res = new FoodMapResponse(mealList, shop);
			resList.add(res);
		}

		resListObject.setSearchResList(resList);
		resListObject.setMessage(FoodMapRtnCode.SUCCESSFUL.getMessage());
		
		return resListObject;
	}

}
