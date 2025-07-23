# 🚀 Sistema de Notificación de Planes de Acción MRM

## 📋 Descripción General

Este proyecto es un microservicio desarrollado en **Java y Spring Boot** cuyo objetivo es automatizar el envío de notificaciones por correo electrónico para los planes de acción de la Gestión de Riesgos de Modelos (MRM). El sistema se conecta a una base de datos, identifica los planes próximos a vencer o ya vencidos, y envía alertas consolidadas y personalizadas a los usuarios responsables.

## 🎯 Funcionalidades Clave

-   ✅ **Proceso Automatizado:** Se ejecuta automáticamente al iniciar, sin intervención manual.
-   ✅ **Notificaciones Inteligentes:** Envía correos únicamente a los usuarios con planes activos que requieren atención.
-   ✅ **Alertas Consolidadas:** Agrupa todos los planes de un usuario en un único correo para evitar el spam.
-   ✅ **Clasificación de Urgencia:** Distingue entre planes "Vencidos" y "Próximos a Vencer".
-   ✅ **Plantillas Profesionales:** Utiliza plantillas HTML (Thymeleaf) para generar correos claros y fáciles de leer.
-   ✅ **Alta Configurabilidad:** Permite ajustar parámetros críticos (días de aviso, roles, remitente) desde un archivo de propiedades.

## 🛠️ Stack Tecnológico

-   **Lenguaje:** Java 17
-   **Framework:** Spring Boot 3.2.0
-   **Gestión de Dependencias:** Maven
-   **Base de Datos:**
    -   **Desarrollo:** H2 (Base de datos en memoria)
    -   **Producción:** Preparado para Apache Impala (o cualquier BD con driver JDBC)
-   **Plantillas de Correo:** Thymeleaf

---

## ⚙️ Configuración

Antes de ejecutar, es necesario configurar las credenciales de correo en el archivo `src/main/resources/application.properties`.

1.  **Configura el remitente:**
    ```properties
    # Usuario y contraseña de la cuenta de correo que enviará las notificaciones
    spring.mail.username=tu-correo@gmail.com
    # IMPORTANTE: Usar una "Contraseña de Aplicación" generada por Google, no la contraseña normal
    spring.mail.password=tu-contraseña-de-aplicacion
    ```

2.  **Ajusta los parámetros de negocio (opcional):**
    ```properties
    # Días de antelación para notificar un plan "próximo a vencer"
    app.notificaciones.dias-aviso=15
    # Lista de roles que recibirán notificaciones
    app.notificaciones.roles-a-notificar=PROPIETARIO,RESPONSABLE,APROBADOR
    ```

## 🚀 Ejecución

El proyecto está diseñado para ejecutarse como un proceso autónomo.

1.  **Abre una terminal** en la raíz del proyecto.
2.  **Ejecuta el siguiente comando de Maven:**
    ```bash
    mvn spring-boot:run
    ```
3.  La aplicación arrancará, se conectará a la base de datos, procesará los planes, enviará los correos necesarios y mostrará el resultado en la consola.

## 💡 Funcionamiento

Este servicio utiliza la interfaz `CommandLineRunner` de Spring Boot. Esto significa que la lógica de negocio principal (contenida en `NotificacionService`) se ejecuta **una sola vez**, inmediatamente después de que la aplicación ha terminado de iniciarse. No expone una API REST para ser invocada, ya que su propósito es ser un proceso programado (ej. un CronJob diario).