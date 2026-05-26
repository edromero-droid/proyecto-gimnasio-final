# 🏋️ PRUEBAS COMPLETAS - PROYECTO GESTIÓN GIMNASIO
## URLs base de cada microservicio

| Microservicio | Puerto | Base URL |
|---|---|---|
| ms-socios | 8081 | http://localhost:8081/api/socios |
| ms-planes | 8082 | http://localhost:8082/api/planes |
| ms-pagos | 8083 | http://localhost:8083/api/pagos |
| ms-clases | 8084 | http://localhost:8084/api/clases |
| ms-instructores | 8085 | http://localhost:8085/api/instructores |
| ms-equipamiento | 8086 | http://localhost:8086/api/equipamiento |
| ms-asistencia | 8087 | http://localhost:8087/api/asistencia |
| ms-notificaciones | 8088 | http://localhost:8088/api/notificaciones |

---

## 1️⃣ MS-SOCIOS (Puerto 8081)

### GET - Ver todos los socios
```
GET http://localhost:8081/api/socios
```

### GET - Ver socio por ID (existe)
```
GET http://localhost:8081/api/socios/1
```

### GET - Ver socio por ID (NO existe) → Error
```
GET http://localhost:8081/api/socios/999
```

### GET - Buscar por email (existe)
```
GET http://localhost:8081/api/socios/email/carlos.munoz@email.com
```

### GET - Buscar por email (NO existe) → Error
```
GET http://localhost:8081/api/socios/email/noexiste@email.com
```

### GET - Buscar por estado
```
GET http://localhost:8081/api/socios/estado/ACTIVO
GET http://localhost:8081/api/socios/estado/INACTIVO
```

### POST - Crear socio nuevo ✅
```
POST http://localhost:8081/api/socios
Body:
{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@email.com",
    "telefono": "+56988888888",
    "tipoDocumento": "RUT",
    "numeroDocumento": "12345678-9",
    "direccion": "Av. Principal 123"
}
```

### POST - Email duplicado → Error
```
POST http://localhost:8081/api/socios
Body:
{
    "nombre": "Pedro",
    "apellido": "Soto",
    "email": "carlos.munoz@email.com"
}
```

### POST - Sin campos obligatorios → Error
```
POST http://localhost:8081/api/socios
Body:
{
    "email": "test@email.com"
}
```

### PUT - Actualizar socio
```
PUT http://localhost:8081/api/socios/1
Body:
{
    "nombre": "Carlos",
    "apellido": "Muñoz Actualizado",
    "email": "carlos.munoz@email.com",
    "telefono": "+56999999999",
    "estado": "ACTIVO",
    "nivelMembresia": "PREMIUM"
}
```

### DELETE - Eliminar socio
```
DELETE http://localhost:8081/api/socios/1
```

---

## 2️⃣ MS-PLANES (Puerto 8082)

### GET - Ver todos los planes
```
GET http://localhost:8082/api/planes
```

### GET - Ver plan por ID (existe)
```
GET http://localhost:8082/api/planes/1
```

### GET - Ver plan por ID (NO existe) → Error
```
GET http://localhost:8082/api/planes/999
```

### POST - Crear plan nuevo ✅
```
POST http://localhost:8082/api/planes
Body:
{
    "nombre": "Plan Test",
    "descripcion": "Plan de prueba",
    "precio": 29990,
    "diasDuracion": 30,
    "accesoClases": true,
    "accesoPiscina": false
}
```

### POST - Sin nombre → Error
```
POST http://localhost:8082/api/planes
Body:
{
    "precio": 29990,
    "diasDuracion": 30
}
```

### PUT - Actualizar plan
```
PUT http://localhost:8082/api/planes/1
Body:
{
    "nombre": "Plan Básico Actualizado",
    "descripcion": "Nueva descripción",
    "precio": 35000,
    "diasDuracion": 30,
    "accesoClases": false,
    "accesoPiscina": false
}
```

### DELETE - Eliminar plan (existe)
```
DELETE http://localhost:8082/api/planes/1
```

### DELETE - Eliminar plan (NO existe) → Error
```
DELETE http://localhost:8082/api/planes/999
```

---

## 3️⃣ MS-PAGOS (Puerto 8083)

### GET - Ver todos los pagos
```
GET http://localhost:8083/api/pagos
```

### GET - Ver pago por ID (existe)
```
GET http://localhost:8083/api/pagos/1
```

### GET - Ver pago por ID (NO existe) → Error
```
GET http://localhost:8083/api/pagos/999
```

### GET - Pagos de un socio
```
GET http://localhost:8083/api/pagos/socio/1
```

### POST - Registrar pago (socio existe) ✅
```
POST http://localhost:8083/api/pagos
Body:
{
    "socioId": 1,
    "tipoPago": "MENSUALIDAD",
    "monto": 69990,
    "concepto": "Mensualidad mayo 2026"
}
```

### POST - Socio NO existe → Error
```
POST http://localhost:8083/api/pagos
Body:
{
    "socioId": 999,
    "tipoPago": "MENSUALIDAD",
    "monto": 69990
}
```

### POST - Email incorrecto → Error
```
POST http://localhost:8083/api/pagos
Body:
{
    "socioId": 1,
    "socioEmailVerificacion": "otro@email.com",
    "tipoPago": "MENSUALIDAD",
    "monto": 69990
}
```

### PATCH - Anular pago
```
PATCH http://localhost:8083/api/pagos/1/anular
```

---

## 4️⃣ MS-CLASES (Puerto 8084)

### GET - Ver todas las sesiones
```
GET http://localhost:8084/api/clases/sesiones
```

### GET - Ver sesiones disponibles (PROGRAMADA)
```
GET http://localhost:8084/api/clases/sesiones/disponibles
```

### GET - Ver sesión por ID (existe)
```
GET http://localhost:8084/api/clases/sesiones/1
```

### GET - Ver sesión por ID (NO existe) → Error
```
GET http://localhost:8084/api/clases/sesiones/999
```

### GET - Reservas de un socio (existe y tiene reservas)
```
GET http://localhost:8084/api/clases/reservas/socio/1
```

### GET - Reservas de socio sin reservas → Error
```
GET http://localhost:8084/api/clases/reservas/socio/5
```

### GET - Reservas de socio NO existe → Error
```
GET http://localhost:8084/api/clases/reservas/socio/999
```

### POST - Reservar clase (socio existe) ✅
```
POST http://localhost:8084/api/clases/reservas
Body:
{
    "sesionId": 1,
    "socioId": 2
}
```

### POST - Socio NO existe → Error
```
POST http://localhost:8084/api/clases/reservas
Body:
{
    "sesionId": 1,
    "socioId": 999
}
```

### POST - Sesión NO existe → Error
```
POST http://localhost:8084/api/clases/reservas
Body:
{
    "sesionId": 999,
    "socioId": 1
}
```

### POST - Email incorrecto → Error
```
POST http://localhost:8084/api/clases/reservas
Body:
{
    "sesionId": 1,
    "socioId": 1,
    "socioEmailVerificacion": "otro@email.com"
}
```

---

## 5️⃣ MS-INSTRUCTORES (Puerto 8085)

### GET - Ver instructores activos
```
GET http://localhost:8085/api/instructores
```

### GET - Ver todos los instructores
```
GET http://localhost:8085/api/instructores?todos=true
```

### GET - Ver instructor por ID (existe)
```
GET http://localhost:8085/api/instructores/1
```

### GET - Ver instructor por ID (NO existe) → Error
```
GET http://localhost:8085/api/instructores/999
```

### POST - Crear instructor ✅
```
POST http://localhost:8085/api/instructores
Body:
{
    "nombre": "Pedro",
    "apellido": "Soto",
    "email": "pedro.soto@gimnasio.cl",
    "tipoContrato": "PLANTA",
    "especialidades": "Yoga, Pilates"
}
```

### POST - Email duplicado → Error
```
POST http://localhost:8085/api/instructores
Body:
{
    "nombre": "Otro",
    "apellido": "Instructor",
    "email": "rodrigo.herrera@gimnasio.cl",
    "tipoContrato": "PLANTA",
    "especialidades": "CrossFit"
}
```

### POST - Sin especialidades → Error
```
POST http://localhost:8085/api/instructores
Body:
{
    "nombre": "Test",
    "apellido": "Test",
    "email": "test@gimnasio.cl",
    "tipoContrato": "PLANTA"
}
```

### PUT - Actualizar instructor
```
PUT http://localhost:8085/api/instructores/1
Body:
{
    "nombre": "Rodrigo",
    "apellido": "Herrera",
    "email": "rodrigo.herrera@gimnasio.cl",
    "tipoContrato": "PLANTA",
    "especialidades": "CrossFit, Funcional, HIIT",
    "estado": "ACTIVO"
}
```

### DELETE - Dar de baja instructor
```
DELETE http://localhost:8085/api/instructores/1
```

---

## 6️⃣ MS-EQUIPAMIENTO (Puerto 8086)

### GET - Ver equipamiento disponible
```
GET http://localhost:8086/api/equipamiento
```

### GET - Ver todo el equipamiento
```
GET http://localhost:8086/api/equipamiento?todos=true
```

### GET - Ver equipo por ID (existe)
```
GET http://localhost:8086/api/equipamiento/1
```

### GET - Ver equipo por ID (NO existe) → Error
```
GET http://localhost:8086/api/equipamiento/999
```

### POST - Crear equipo ✅
```
POST http://localhost:8086/api/equipamiento
Body:
{
    "codigo": "TEST-001",
    "nombre": "Equipo Test",
    "tipo": "CARDIO",
    "salaNombre": "Sala Test"
}
```

### POST - Código duplicado → Error
```
POST http://localhost:8086/api/equipamiento
Body:
{
    "codigo": "CARD-001",
    "nombre": "Equipo Duplicado",
    "tipo": "CARDIO",
    "salaNombre": "Sala Test"
}
```

### PUT - Actualizar equipo
```
PUT http://localhost:8086/api/equipamiento/1
Body:
{
    "codigo": "CARD-001",
    "nombre": "Cinta actualizada",
    "tipo": "CARDIO",
    "salaNombre": "Sala Cardio",
    "estado": "MANTENIMIENTO"
}
```

### DELETE - Eliminar equipo (existe)
```
DELETE http://localhost:8086/api/equipamiento/1
```

### DELETE - Eliminar equipo (NO existe) → Error
```
DELETE http://localhost:8086/api/equipamiento/999
```

---

## 7️⃣ MS-ASISTENCIA (Puerto 8087)

### GET - Ver todos los registros
```
GET http://localhost:8087/api/asistencia
```

### GET - Historial de socio (existe y tiene registros)
```
GET http://localhost:8087/api/asistencia/historial/1
```

### GET - Historial de socio sin registros → Error
```
GET http://localhost:8087/api/asistencia/historial/5
```

### GET - Historial de socio NO existe → Error
```
GET http://localhost:8087/api/asistencia/historial/999
```

### GET - Ver accesos rechazados
```
GET http://localhost:8087/api/asistencia/rechazados
```

### POST - Registrar acceso (socio existe) ✅
```
POST http://localhost:8087/api/asistencia/acceso
Body:
{
    "socioId": 1,
    "tipoAcceso": "ENTRADA",
    "metodoAcceso": "QR"
}
```

### POST - Socio NO existe → Error
```
POST http://localhost:8087/api/asistencia/acceso
Body:
{
    "socioId": 999,
    "tipoAcceso": "ENTRADA",
    "metodoAcceso": "QR"
}
```

### POST - Email incorrecto → Error
```
POST http://localhost:8087/api/asistencia/acceso
Body:
{
    "socioId": 1,
    "socioEmailVerificacion": "otro@email.com",
    "tipoAcceso": "ENTRADA",
    "metodoAcceso": "QR"
}
```

---

## 8️⃣ MS-NOTIFICACIONES (Puerto 8088)

### GET - Ver todas las notificaciones
```
GET http://localhost:8088/api/notificaciones
```

### GET - Ver notificaciones pendientes
```
GET http://localhost:8088/api/notificaciones/pendientes
```

### GET - Notificaciones de destinatario (existe y tiene notificaciones)
```
GET http://localhost:8088/api/notificaciones/destinatario/1
```

### GET - Destinatario sin notificaciones → Error
```
GET http://localhost:8088/api/notificaciones/destinatario/5
```

### GET - Destinatario NO existe → Error
```
GET http://localhost:8088/api/notificaciones/destinatario/999
```

### POST - Crear notificación (socio existe) ✅
```
POST http://localhost:8088/api/notificaciones
Body:
{
    "destinatarioId": 1,
    "tipo": "BIENVENIDA",
    "asunto": "Bienvenido al gimnasio",
    "cuerpo": "Hola, bienvenido a GimnasioPro"
}
```

### POST - Socio NO existe → Error
```
POST http://localhost:8088/api/notificaciones
Body:
{
    "destinatarioId": 999,
    "tipo": "BIENVENIDA",
    "asunto": "Bienvenido",
    "cuerpo": "Hola"
}
```

### POST - Email incorrecto → Error
```
POST http://localhost:8088/api/notificaciones
Body:
{
    "destinatarioId": 1,
    "destinatarioEmailVerificacion": "otro@email.com",
    "tipo": "BIENVENIDA",
    "asunto": "Bienvenido",
    "cuerpo": "Hola"
}
```

### POST - Sin campos obligatorios → Error
```
POST http://localhost:8088/api/notificaciones
Body:
{
    "destinatarioId": 1
}
```
