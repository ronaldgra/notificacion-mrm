-- Datos de prueba para la base de datos H2 en memoria
-- Spring Boot ejecutará este script automáticamente al iniciar la aplicación.

-- Nota: Los nombres de tabla 'planes_accion' y 'usuarios_planes' coinciden con los definidos
-- en la anotación @Table(name = "...") de las entidades Java.

INSERT INTO planes_accion (id_plan_accion, titulo_plan_acc, prioridad_plan_acc, estado_plan_acc, f_vencimiento_plan_acc, f_cierre_plan_acc, usuario_activo_update_plan_acc) VALUES
(101, 'Revisar Modelo de Riesgo Crediticio', 'Alta', 'Abierto', CURRENT_DATE() + 10, NULL, true),
(102, 'Ajustar Parámetros de Volatilidad', 'Crítica', 'Abierto', CURRENT_DATE() - 5, NULL, true),
(103, 'Documentar Proceso de Backtesting', 'Media', 'Abierto', CURRENT_DATE() + 30, NULL, true),
(104, 'Plan de Acción Cerrado (Debe ser ignorado)', 'Baja', 'Cerrado', CURRENT_DATE() - 50, CURRENT_DATE() - 40, true);

INSERT INTO usuarios_planes (id, id_plan_accion, usuario_rel_plan_acc, nombre_usuario_rel_plan_acc, email_usuario_rel_plan_acc, tipo_relacion_usuario, usuario_activo_rel_plan_acc) VALUES
(1, 101, 'JORGEPER', 'Jorge Perez', 'ronaldgra@gmail.com', 'RESPONSABLE', true),
(2, 101, 'LUISARES', 'Luisa Restrepo', 'ronaldgra@gmail.com', 'APROBADOR', true),
(3, 102, 'JORGEPER', 'Jorge Perez', 'ronaldgra@gmail.com', 'RESPONSABLE', true),
(4, 102, 'RGRANADA', 'Ricardo Granada', 'ronaldgra@gmail.com', 'APROBADOR', true),
(5, 103, 'LINAARAN', 'Lina Arango', 'ronaldgra@gmail.com', 'PROPIETARIO', true);
