INSERT
	INTO
	testkerja.banner
(id,
	banner_image,
	banner_name,
	created_by,
	created_on,
	description,
	is_active,
	updated_by,
	updated_on)
VALUES
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 1','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 2','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 3','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 4','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 5','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00')),
(UUID(),'https://nutech-integrasi.app/dummy.jpg','Banner 6','SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'),'Lerem Ipsum Dolor sit amet',1,'SYSTEM',CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'));