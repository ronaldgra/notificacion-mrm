# 🚀 Sistema de Notificación MRM - Bancolombia

## 📋 Descripción
Sistema automatizado para la gestión y envío de alertas de planes de acción relacionados con riesgos de modelos (MRM) en Bancolombia.

## 🎯 Funcionalidades
- ✅ Gestión automatizada de planes de acción
- ✅ Alertas por email con templates profesionales
- ✅ Clasificación por vencimiento (15, 30+ días)
- ✅ API REST para integración
- ✅ Trazabilidad completa del flujo

## 🛠️ Tecnologías
- **Backend:** Java 17, Spring Boot 3.2.0
- **Base de Datos:** H2 (desarrollo), Apache Impala (producción)
- **Build:** Maven 3.9.5
- **Templates:** Thymeleaf, HTML/CSS

## 🚀 Ejecución
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run

# Endpoint manual
POST http://localhost:8080/api/notificaciones/enviar