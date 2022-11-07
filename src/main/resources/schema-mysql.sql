CREATE TABLE IF NOT EXISTS `foodmap_meal` (
  `shop_name` varchar(30) NOT NULL,
  `meal_name` varchar(30) NOT NULL,
  `price` int DEFAULT NULL,
  `meal_level` int DEFAULT NULL,
  PRIMARY KEY (`shop_name`,`meal_name`)
);

CREATE TABLE IF NOT EXISTS `foodmap_shop` (
  `shop_name` varchar(30) NOT NULL,
  `city` varchar(30) DEFAULT NULL,
  `shop_level` double DEFAULT NULL,
  PRIMARY KEY (`shop_name`)
);
