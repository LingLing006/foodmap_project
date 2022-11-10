package com.example.foodmap_project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="foodmap_shop")
public class FoodMapShop {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//  @GenericGenerator(name="system_uuid",strategy="uuid")
	@Column(name="shop_name")
	private String shopName;
	
	@Column(name="city")
	private String city;
	
	@Column(name="shop_level")
	private double shopLevel;

//=========================================	
	public FoodMapShop() {
		
	}
	
	public FoodMapShop(String shopName,String city) {
		this.shopName = shopName;
		this.city = city;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(double shopLevel) {
		this.shopLevel = shopLevel;
	}
	
	
	
}
