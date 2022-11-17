package com.example.foodmap_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmap_project.entity.FoodMapShop;

@Repository
public interface FoodMapShopDao extends JpaRepository<FoodMapShop, String> {

	// �ϥΫ����j�M���a�A�åΩ��a��������Ƨ�
	public List<FoodMapShop> findByCityOrderByShopLevelDesc(String city);

	// �ϥΩ��a�����j�M��������X���P(�t)�H�W�����a�A�åΩ��a��������Ƨ�
	public List<FoodMapShop> findByShopLevelGreaterThanEqualOrderByShopLevelDesc(double shopLevel);
}
