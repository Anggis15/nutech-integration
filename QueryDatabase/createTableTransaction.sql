
CREATE TABLE `transaction` (
  `invoice_number` varchar(255) NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `total_amount` bigint DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  `id_user_profile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`invoice_number`),
  KEY `fk_to_user_profile` (`id_user_profile`),
  CONSTRAINT `fk_to_user_profile` FOREIGN KEY (`id_user_profile`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;