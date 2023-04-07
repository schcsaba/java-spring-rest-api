-- --------------------------------------------------------
-- Hôte:                         localhost
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour shop
DROP DATABASE IF EXISTS `shop`;
CREATE DATABASE IF NOT EXISTS `shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `shop`;

-- Listage de la structure de table shop. categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `vat` int unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.categories : ~3 rows (environ)
INSERT INTO `categories` (`id`, `name`, `vat`) VALUES
	(1, 'food', 550),
	(2, 'book', 2000),
	(3, 'computer', 2000);

-- Listage de la structure de table shop. orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned DEFAULT '0',
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_orders_users` (`user_id`),
  CONSTRAINT `FK_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.orders : ~2 rows (environ)
INSERT INTO `orders` (`id`, `user_id`, `created_at`) VALUES
	(1, 1, '2023-04-03 14:37:22'),
	(2, 3, '2023-04-03 14:37:35');

-- Listage de la structure de table shop. order_items
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int unsigned DEFAULT '0',
  `amount` int unsigned NOT NULL DEFAULT '0',
  `order_id` int unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_order_items_products` (`product_id`),
  KEY `FK_order_items_orders` (`order_id`),
  CONSTRAINT `FK_order_items_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_order_items_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.order_items : ~3 rows (environ)
INSERT INTO `order_items` (`id`, `product_id`, `amount`, `order_id`) VALUES
	(1, 1, 1, 1),
	(2, 2, 6, 1),
	(3, 3, 1, 2);

-- Listage de la structure de table shop. products
DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'name',
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` int unsigned NOT NULL DEFAULT '0',
  `category_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_products_categories` (`category_id`),
  CONSTRAINT `FK_products_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.products : ~5 rows (environ)
INSERT INTO `products` (`id`, `name`, `description`, `price`, `category_id`) VALUES
	(1, 'Book A', 'A very interesting book', 2400, 2),
	(2, 'Croissant', 'Taste these delicious, bio and vegan croissants', 200, 1),
	(3, 'MacBook Air', 'A high quality notebook', 150000, 3),
	(4, 'Book B', 'A crime story', 2800, 2),
	(5, 'Book C', 'A romantic story', 2600, NULL);

-- Listage de la structure de table shop. reviews
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `review` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_id` int unsigned DEFAULT '0',
  `rating` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK__products` (`product_id`),
  CONSTRAINT `FK__products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.reviews : ~9 rows (environ)
INSERT INTO `reviews` (`id`, `review`, `product_id`, `rating`) VALUES
	(1, 'Very good computer', 3, 9),
	(3, 'Very tasty', 2, 10),
	(4, 'Very good book', 1, 8),
	(5, 'Nice', 2, 8),
	(6, 'Interesting book', 1, 7),
	(7, 'Good for office work', 3, 8),
	(8, 'Delicious, but small', 2, 7),
	(9, 'Very small', 2, 6),
	(10, 'Not very original', 1, 6);

-- Listage de la structure de table shop. users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `last_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table shop.users : ~3 rows (environ)
INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`) VALUES
	(1, 'John', 'Doe', 'johndoe@email.com'),
	(2, 'John', 'Smith', 'johnsmith@email.com'),
	(3, 'Sarah', 'Denver', 'sarahdenver@email.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
