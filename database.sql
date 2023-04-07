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

-- Listage des données de la table shop.categories : ~3 rows (environ)
INSERT INTO `categories` (`id`, `name`, `vat`) VALUES
	(1, 'food', 550),
	(2, 'book', 2000),
	(3, 'computer', 2000);

-- Listage des données de la table shop.orders : ~2 rows (environ)
INSERT INTO `orders` (`id`, `user_id`, `created_at`) VALUES
	(1, 1, '2023-04-03 14:37:22'),
	(2, 3, '2023-04-03 14:37:35');

-- Listage des données de la table shop.order_items : ~3 rows (environ)
INSERT INTO `order_items` (`id`, `product_id`, `amount`, `order_id`) VALUES
	(1, 1, 1, 1),
	(2, 2, 6, 1),
	(3, 3, 1, 2);

-- Listage des données de la table shop.products : ~5 rows (environ)
INSERT INTO `products` (`id`, `name`, `description`, `price`, `category_id`) VALUES
	(1, 'Book A', 'A very interesting book', 2400, 2),
	(2, 'Croissant', 'Taste these delicious, bio and vegan croissants', 200, 1),
	(3, 'MacBook Air', 'A high quality notebook', 150000, 3),
	(4, 'Book B', 'A crime story', 2800, 2),
	(5, 'Book C', 'A romantic story', 2600, NULL);

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

-- Listage des données de la table shop.users : ~2 rows (environ)
INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`) VALUES
	(1, 'John', 'Doe', 'johndoe@email.com'),
	(2, 'John', 'Smith', 'johnsmith@email.com'),
	(3, 'Sarah', 'Denver', 'sarahdenver@email.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
