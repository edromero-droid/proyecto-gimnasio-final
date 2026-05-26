INSERT INTO planes (id, nombre, descripcion, precio, dias_duracion, acceso_clases, acceso_piscina, activo, created_at) VALUES
(1, 'Plan Básico', 'Acceso a sala de musculación y cardio en horario estándar.', 29990, 30, false, false, true, NOW()),
(2, 'Plan Estándar', 'Acceso completo a todas las salas + clases grupales incluidas.', 49990, 30, true, false, true, NOW()),
(3, 'Plan Premium', 'Todo el plan Estándar más acceso a piscina y vestuarios premium.', 69990, 30, true, true, true, NOW()),
(4, 'Plan VIP Anual', 'Membresía anual con todos los beneficios Premium + entrenador personal 2x mes.', 599990, 365, true, true, true, NOW()),
(5, 'Plan Estudiante', 'Acceso a sala de musculación de lunes a viernes (horario diurno).', 19990, 30, false, false, true, NOW()),
(6, 'Plan Familia', 'Hasta 4 integrantes del mismo núcleo familiar. Acceso completo + clases.', 89990, 30, true, false, true, NOW());
