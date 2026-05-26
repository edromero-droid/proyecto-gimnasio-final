INSERT INTO registros_acceso (registro_id, socio_id, socio_nombre, socio_email, plan_nombre, membresia_fin, tipo_acceso, metodo_acceso, punto_acceso, permitido, motivo_denegacion, timestamp_evento, created_at) VALUES
(1, 1, 'Carlos Muñoz', 'carlos.munoz@email.com', 'Plan Premium', '2025-12-31', 'ENTRADA', 'QR', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-12 07:45:00', NOW()),
(2, 1, 'Carlos Muñoz', 'carlos.munoz@email.com', 'Plan Premium', '2025-12-31', 'SALIDA', 'QR', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-12 09:30:00', NOW()),
(3, 2, 'Valentina Rojas', 'vale.rojas@email.com', 'Plan Estándar', '2025-05-31', 'ENTRADA', 'RFID', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-12 08:10:00', NOW()),
(4, 2, 'Valentina Rojas', 'vale.rojas@email.com', 'Plan Estándar', '2025-05-31', 'SALIDA', 'RFID', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-12 09:05:00', NOW()),
(5, 7, 'Felipe Vargas', 'felipe.vargas@gmail.com', 'Plan Estándar', '2025-04-30', 'ENTRADA', 'QR', 'ENTRADA_PRINCIPAL', false, 'MEMBRESIA_VENCIDA', '2025-05-12 10:00:00', NOW()),
(6, 5, 'Matías Torres', 'matias.torres@outlook.com', 'Plan VIP Anual', '2025-12-31', 'ENTRADA', 'QR', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-13 06:30:00', NOW()),
(7, 5, 'Matías Torres', 'matias.torres@outlook.com', 'Plan VIP Anual', '2025-12-31', 'ENTRADA', 'QR', 'PISCINA', true, NULL, '2025-05-13 07:30:00', NOW()),
(8, 3, 'Sebastián González', 'seba.gonzalez@gmail.com', 'Plan Premium', '2025-04-30', 'ENTRADA', 'MANUAL', 'ENTRADA_PRINCIPAL', false, 'MEMBRESIA_VENCIDA', '2025-05-14 09:00:00', NOW()),
(9, 8, 'Antonia Pérez', 'antonia.perez@email.com', 'Plan Premium', '2025-06-30', 'ENTRADA', 'RFID', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-14 07:00:00', NOW()),
(10, 10, 'Sofía Ramírez', 'sofia.ramirez@email.com', 'Plan VIP Anual', '2025-12-31', 'ENTRADA', 'QR', 'ENTRADA_PRINCIPAL', true, NULL, '2025-05-14 08:15:00', NOW());
