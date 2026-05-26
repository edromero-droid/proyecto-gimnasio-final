INSERT INTO sesiones_clase (clase_nombre, clase_tipo, clase_nivel, duracion_minutos, instructor_id, instructor_nombre, sala_nombre, fecha_hora_inicio, fecha_hora_fin, capacidad_maxima, reservas_activas, estado, costo_adicional, created_at) VALUES
('Yoga Matutino', 'Yoga', 'TODOS', 60, 2, 'Marcela Silva', 'Sala Yoga', '2025-05-19 08:00:00', '2025-05-19 09:00:00', 15, 3, 'PROGRAMADA', 0.00, NOW()),
('Yoga Matutino', 'Yoga', 'TODOS', 60, 2, 'Marcela Silva', 'Sala Yoga', '2025-05-21 08:00:00', '2025-05-21 09:00:00', 15, 2, 'PROGRAMADA', 0.00, NOW()),
('CrossFit Intensivo', 'CrossFit', 'AVANZADO', 60, 1, 'Rodrigo Herrera', 'Sala Funcional', '2025-05-20 18:00:00', '2025-05-20 19:00:00', 12, 5, 'PROGRAMADA', 0.00, NOW()),
('CrossFit Intensivo', 'CrossFit', 'AVANZADO', 60, 1, 'Rodrigo Herrera', 'Sala Funcional', '2025-05-22 18:00:00', '2025-05-22 19:00:00', 12, 4, 'PROGRAMADA', 0.00, NOW()),
('Spinning Express', 'Spinning', 'TODOS', 45, 4, 'Paula Jiménez', 'Sala Cardio', '2025-05-19 19:00:00', '2025-05-19 19:45:00', 20, 8, 'PROGRAMADA', 0.00, NOW()),
('Pilates Core', 'Pilates', 'INTERMEDIO', 50, 2, 'Marcela Silva', 'Sala Yoga', '2025-05-14 09:00:00', '2025-05-14 09:50:00', 10, 6, 'FINALIZADA', 0.00, NOW());

INSERT INTO reservas_clase (sesion_id, clase_nombre, instructor_nombre, fecha_hora_inicio, socio_id, socio_nombre, socio_email, estado, fecha_reserva, created_at) VALUES
(1, 'Yoga Matutino', 'Marcela Silva', '2025-05-19 08:00:00', 2, 'Valentina Rojas', 'vale.rojas@email.com', 'CONFIRMADA', '2025-05-15 20:00:00', NOW()),
(1, 'Yoga Matutino', 'Marcela Silva', '2025-05-19 08:00:00', 8, 'Antonia Pérez', 'antonia.perez@email.com', 'CONFIRMADA', '2025-05-15 21:00:00', NOW()),
(3, 'CrossFit Intensivo', 'Rodrigo Herrera', '2025-05-20 18:00:00', 1, 'Carlos Muñoz', 'carlos.munoz@email.com', 'CONFIRMADA', '2025-05-14 15:00:00', NOW()),
(3, 'CrossFit Intensivo', 'Rodrigo Herrera', '2025-05-20 18:00:00', 5, 'Matías Torres', 'matias.torres@outlook.com', 'CONFIRMADA', '2025-05-14 16:00:00', NOW()),
(5, 'Spinning Express', 'Paula Jiménez', '2025-05-19 19:00:00', 4, 'Camila Fernández', 'cami.fern@gmail.com', 'CANCELADA', '2025-05-13 18:00:00', NOW()),
(6, 'Pilates Core', 'Marcela Silva', '2025-05-14 09:00:00', 2, 'Valentina Rojas', 'vale.rojas@email.com', 'ASISTIO', '2025-05-10 12:00:00', NOW());
