-- =================================================================================
-- SCRIPT DE DATOS DE PRUEBA PARA H2
-- Este script primero elimina las tablas si existen, luego las crea y finalmente
-- las puebla con datos que cubren múltiples escenarios de prueba.
-- =================================================================================

-- Paso 1: Eliminar las tablas si ya existen para asegurar un inicio limpio.
DROP TABLE IF EXISTS usuarios_planes;
DROP TABLE IF EXISTS planes_accion;

-- Paso 2: Crear la estructura de las tablas.
CREATE TABLE planes_accion (
    id_plan_accion INT PRIMARY KEY,
    titulo_plan_acc VARCHAR(255),
    prioridad_plan_acc VARCHAR(50),
    estado_plan_acc VARCHAR(50),
    f_vencimiento_plan_acc DATE,
    f_cierre_plan_acc DATE,
    usuario_activo_update_plan_acc BOOLEAN
);

CREATE TABLE usuarios_planes (
    id INT PRIMARY KEY,
    id_plan_accion INT,
    usuario_rel_plan_acc VARCHAR(50),
    nombre_usuario_rel_plan_acc VARCHAR(100),
    email_usuario_rel_plan_acc VARCHAR(100),
    tipo_relacion_usuario VARCHAR(50),
    usuario_activo_rel_plan_acc BOOLEAN,
    FOREIGN KEY (id_plan_accion) REFERENCES planes_accion(id_plan_accion)
);

-- Paso 3: Insertar los datos de prueba.

-- Inserción en 'planes_accion'
INSERT INTO planes_accion (id_plan_accion, titulo_plan_acc, prioridad_plan_acc, estado_plan_acc, f_vencimiento_plan_acc, f_cierre_plan_acc, usuario_activo_update_plan_acc) VALUES
-- Escenario 1: Plan VENCIDO (hace 7 días), prioridad Crítica.
(301, 'Corregir Desviación Crítica en Modelo de Mercado', 'Crítica', 'Abierto', CURRENT_DATE() - 7, NULL, true),

-- Escenario 2: Plan PRÓXIMO A VENCER (en 10 días), prioridad Alta.
(302, 'Validar Nuevo Modelo de Scoring de Riesgo', 'Alta', 'Abierto', CURRENT_DATE() + 10, NULL, true),

-- Escenario 3: Plan ACTIVO pero no próximo (en 45 días), debe ser ignorado por la notificación de proximidad.
(303, 'Actualizar Documentación de Modelos Antiguos', 'Media', 'Abierto', CURRENT_DATE() + 45, NULL, true),

-- Escenario 4: Plan CERRADO, debe ser ignorado completamente por la consulta inicial.
(304, 'Implementación Inicial de Modelo X (Cerrado)', 'Baja', 'Cerrado', CURRENT_DATE() - 100, CURRENT_DATE() - 90, true),

-- Escenario 5: Plan con usuario INACTIVO.
(305, 'Revisar Plan con Responsable Inactivo', 'Media', 'Abierto', CURRENT_DATE() + 5, NULL, true),

-- Escenario 6: Plan con APROBADOR INCORRECTO (no pertenece al equipo validador).
(306, 'Plan con Aprobador Fuera del Equipo', 'Alta', 'Abierto', CURRENT_DATE() - 2, NULL, true),

-- Escenario 7: PLAN DE PRUEBA PERFECTO (debe generar notificación).
(307, 'Plan de Prueba Perfecto para Notificación', 'Alta', 'Abierto', CURRENT_DATE() + 5, NULL, true);


-- Inserción en 'usuarios_planes'
INSERT INTO usuarios_planes (id, id_plan_accion, usuario_rel_plan_acc, nombre_usuario_rel_plan_acc, email_usuario_rel_plan_acc, tipo_relacion_usuario, usuario_activo_rel_plan_acc) VALUES
-- Asignaciones para el Plan 301 (Vencido)
(1, 301, 'JORGEPER', 'Jorge Perez', 'rgranada@bancolombia.com.co', 'RESPONSABLE', true),
(2, 301, 'RGRANADA', 'Ronald Granada', 'rgranada@bancolombia.com.co', 'APROBADOR', true), -- Validador

-- Asignaciones para el Plan 302 (Próximo a Vencer)
(3, 302, 'LINAARAN', 'Lina Arango', 'rgranada@bancolombia.com.co', 'RESPONSABLE', true),
(4, 302, 'LUISARES', 'Luisa Restrepo', 'rgranada@bancolombia.com.co', 'APROBADOR', true), -- Validador

-- Asignaciones para el Plan 303 (Lejano)
(5, 303, 'JGCADAVI', 'Juan Cadavid', 'rgranada@bancolombia.com.co', 'PROPIETARIO', true),

-- Asignaciones para el Plan 305 (Usuario Inactivo)
(6, 305, 'JDOE', 'John Doe Inactivo', 'rgranada@bancolombia.com.co', 'RESPONSABLE', false), -- Usuario inactivo
(7, 305, 'JORGEPER', 'Jorge Perez', 'rgranada@bancolombia.com.co', 'APROBADOR', true), -- Validador

-- Asignaciones para el Plan 306 (Aprobador Incorrecto)
(8, 306, 'JORGEPER', 'Jorge Perez', 'rgranada@bancolombia.com.co', 'RESPONSABLE', true),
(9, 306, 'USER_EXT', 'Usuario Externo', 'rgranada@bancolombia.com.co', 'APROBADOR', true), -- Este usuario no es un validador

-- Asignaciones para el Plan 307 (Prueba Perfecta)
(10, 307, 'JORGEPER', 'Jorge Perez', 'rgranada@bancolombia.com.co', 'RESPONSABLE', true); -- Usuario activo, rol válido, plan próximo a vencer
