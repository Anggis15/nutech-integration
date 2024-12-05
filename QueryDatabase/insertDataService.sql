INSERT
	INTO
	testkerja.mst_service
(id,
	created_by,
	created_on,
	is_active,
	service_code,
	service_icon,
	service_name,
	service_tariff,
	updated_by,
	updated_on)
VALUES
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PAJAK', 'https://minio.nutech-integrasi.com/take-home-test/services/PBB.png', 'Pajak PBB', 40000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PLN', 'https://minio.nutech-integrasi.com/take-home-test/services/Listrik.png', 'Listrik', 10000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PDAM', 'https://minio.nutech-integrasi.com/take-home-test/services/PDAM.png', 'PDAM Berlangganan', 40000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PULSA', 'https://minio.nutech-integrasi.com/take-home-test/services/Pulsa.png', 'pulsa', 40000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PGN', 'https://minio.nutech-integrasi.com/take-home-test/services/PGN.png', 'PGN Berlangganan', 50000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'MUSIK', 'https://minio.nutech-integrasi.com/take-home-test/services/Musik.png', 'Musik Berlangganan', 50000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'TV', 'https://minio.nutech-integrasi.com/take-home-test/services/Televisi.png', 'TV Berlangganan', 50000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'PAKET_DATA', 'https://minio.nutech-integrasi.com/take-home-test/services/Paket-Data.png', 'Paket Data"', 50000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'VOUCHER_GAME', 'https://minio.nutech-integrasi.com/take-home-test/services/Game.png', 'Voucher Game', 100000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'VOUCHER_MAKANAN', 'https://minio.nutech-integrasi.com/take-home-test/services/Voucher-Makanan.png', 'Voucher Makanan', 100000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'QURBAN', 'https://minio.nutech-integrasi.com/take-home-test/services/Qurban.png', 'Qurban', 200000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(), 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'), 1, 'ZAKAT', 'https://minio.nutech-integrasi.com/take-home-test/services/Zakat.png', 'Zakat', 300000, 'SYSTEM', CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'));