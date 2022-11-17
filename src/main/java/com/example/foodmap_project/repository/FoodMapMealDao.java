package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapMeal;
import com.example.foodmap_project.entity.FoodMapMealId;

@Repository
public interface FoodMapMealDao extends JpaRepository<FoodMapMeal, FoodMapMealId> {

	// �ϥΩ��a�W�ٷj�M�\�I
	public List<FoodMapMeal> findByShopName(String shopName);

	// �ϥΩ��a�W�ٷj�M�\�I
	public List<FoodMapMeal> findByShopNameOrderByMealLevelDesc(String shopName);

	// �ϥΩ��a�W��(�^�夣���j�p�g)�j�M�\�I�A�å��\�I��������Ƨ�
	public List<FoodMapMeal> findByShopNameAllIgnoreCaseOrderByMealLevelDesc(String shopName);

	// �ϥΦs�����a�W�٪�List�j�M�A��List���Ҧ����a���\�I
	public List<FoodMapMeal> findByShopNameIn(List<String> shopNameList);

	// �ϥΦs�����a�W�٪�List�H���\�I�����X�P(�t)�H�W�j�M�A��List���Ҧ����a�A�\�I�����X�P(�t)�H�W���\�I
	public List<FoodMapMeal> findByShopNameInAndMealLevelGreaterThanEqualOrderByMealLevelDesc(List<String> shopNameList,
			int mealLevel);

}
