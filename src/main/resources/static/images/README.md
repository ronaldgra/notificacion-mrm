# Logo de Bancolombia

Este directorio debe contener el archivo `bancolombia-logo.png` para ser usado en los correos electrónicos.

## Instrucciones:
1. Coloca el logo oficial de Bancolombia en formato PNG
2. Renombra el archivo como `bancolombia-logo.png`
3. Tamaño recomendado: 200px de ancho máximo

Si no tienes el logo, puedes usar cualquier imagen PNG temporal para pruebas.
# Gestión Automatizada de Planes de Acción MRM - Bancolombia

Este proyecto implementa un sistema automatizado para la gestión y seguimiento de planes de acción del Modelo de Referencia de Mercado (MRM) en Bancolombia, utilizando Spring Boot. El objetivo es mitigar riesgos operacionales, mejorar la eficiencia y garantizar el cumplimiento regulatorio.

---

## 1. Situación/Problemática y Solución 🎯

### Problemática
En Bancolombia, la gestión manual de planes de acción MRM genera:
- **Incumplimiento de plazos** por falta de seguimiento.
- **Pérdida de trazabilidad** en el estado de los planes.
- **Sobrecarga administrativa** para validadores y responsables.
- **Riesgo operacional** por planes vencidos sin atención.

### ¿Por qué desarrollo de software?
El software automatiza procesos repetitivos, garantiza consistencia en las alertas y proporciona una trazabilidad completa del flujo de información, abordando directamente las ineficiencias del proceso manual.

### ¿Cómo lo lograrás?
- **Sistema automatizado** con Spring Boot para la gestión centralizada de planes.
- **Alertas proactivas** vía email, segmentadas por rangos de vencimiento.
- **Base de datos centralizada** para una trazabilidad total.
- **API REST** para facilitar la integración con sistemas existentes de Bancolombia.

### ¿Qué esperas entregar?
- Una aplicación Java 100% funcional que envía alertas automáticas.
- Un sistema de trazabilidad completo del ciclo de vida de cada plan.
- **Reducción del 80%** en el tiempo dedicado a la gestión manual.
- Un dashboard para el monitoreo de indicadores en tiempo real.

### ¿Para qué deseas trabajar en este reto?
Para **mitigar riesgos operacionales**, **mejorar la eficiencia** del equipo MRM y **garantizar el cumplimiento regulatorio** en la gestión de los planes de acción.

---

## 2. ¿Por qué es un reto de desarrollo? 💻

Este es un **reto de desarrollo** porque requiere:
- **Arquitectura robusta:** Diseño de una solución escalable y mantenible con Spring Boot, JPA y Maven.
- **Integración de sistemas:** Conexión con bases de datos (H2, Impala), servidores de correo (SMTP) y motores de plantillas (Thymeleaf).
- **Lógica de negocio compleja:** Implementación de reglas para clasificación automática de planes y generación de alertas dinámicas según fechas de vencimiento.
- **Automatización de procesos:** Uso de `CommandLineRunner` y Schedulers de Spring para ejecutar tareas en segundo plano de forma periódica.
- **Manejo de errores:** Diseño de un sistema resiliente con capacidad de recuperación automática ante fallos.

---

## 3. Herramientas y Software a Usar 🛠️

### Backend:
- ✅ **Java 17** (Lenguaje principal)
- ✅ **Spring Boot 3.2.0** (Framework de desarrollo)
- ✅ **Maven** (Gestión de dependencias y construcción del proyecto)
- ✅ **JPA/Hibernate** (ORM para persistencia de datos)

### Base de Datos:
- ✅ **H2 Database** (Base de datos en memoria para desarrollo y pruebas)
- ✅ **Apache Impala** (Base de datos para el entorno de producción)

### Frontend/Templates:
- ✅ **Thymeleaf** (Motor de plantillas para generar emails HTML)
- ✅ **HTML/CSS** (Diseño y estilo de las plantillas de correo)

### Herramientas de Desarrollo:
- ✅ **VS Code** (IDE de desarrollo)
- ✅ **Git** (Sistema de control de versiones)
- ✅ **Postman** (Cliente para pruebas de API REST)

---

## 4. Conceptos Aplicados 🧠

- ✅ **Fundamentos de programación:**
  - Estructuras de control y algoritmos de clasificación.
  - Manejo de colecciones (`Lists`, `Maps`, Streams).
  - Programación Orientada a Objetos (POO).
- ✅ **Java y Spring Framework:**
  - Spring Boot, Spring Data JPA, Spring Mail.
  - Uso de Lombok para reducir código repetitivo (*boilerplate*).
  - Manejo robusto de excepciones (`Exception Handling`).
- ✅ **Código Limpio (Clean Code):**
  - Separación de responsabilidades en capas (Controller, Service, Repository).
  - Aplicación de principios **SOLID**.
  - Nomenclatura clara, código comentado y documentación.
- ✅ **Bases de Datos Relacionales:**
  - Definición de entidades JPA (`PlanAccion`, `UsuarioPlan`).
  - Mapeo de relaciones entre tablas (`@OneToMany`, `@ManyToOne`).
  - Creación de consultas optimizadas con Spring Data.
- ✅ **Protocolos de Comunicación:**
  - Diseño de una **API REST** con endpoints HTTP.
  - Uso de **SMTP** para el envío de correos electrónicos.
  - Intercambio de datos en formato **JSON**.

---

## 5. Indicadores del Reto 📊

- ✅ **Eficiencia:**
  - Automatización que reduce en un **80% el tiempo manual**.
  - Procesamiento optimizado de datos en modo *batch*.
  - Transición de un seguimiento reactivo a **alertas proactivas**.
- ✅ **Disminución del Riesgo:**
  - Alertas automáticas que previenen vencimientos.
  - **Trazabilidad completa** del flujo de notificaciones.
  - Validación de datos de entrada y manejo de errores.
- ✅ **Experiencia del Usuario (UX):**
  - Interfaz de correo limpia y profesional.
  - Emails con **diseño corporativo** de Bancolombia.
  - Información clara y accionable para los responsables.

---

## 6. ¿Por qué apunta a estos indicadores? 🤔

- **Eficiencia:** El sistema automatiza tareas repetitivas, liberando a los equipos especializados para que se centren en actividades de mayor valor añadido.
- **Disminución del Riesgo:** Las alertas proactivas (con 15 y más de 30 días de antelación) garantizan un seguimiento continuo y previenen incumplimientos regulatorios críticos.
- **Experiencia del Usuario:** Los correos electrónicos profesionales con información clara y concisa permiten a los responsables tomar decisiones informadas de manera rápida y eficiente.

---

## 7. Observaciones Adicionales 💡

### Fortalezas Técnicas:
- **Arquitectura escalable** con Spring Boot.
- **Código mantenible** gracias a la separación de capas.
- **Configuración externalizada** para adaptarse a diferentes entornos (desarrollo, producción).
- **Manejo robusto de errores** con mecanismos de reintentos automáticos.

### Beneficios Organizacionales:
- **ROI inmediato** por la drástica reducción del tiempo de gestión manual.
- Cumplimiento regulatorio (**Compliance**) garantizado.
- **Trazabilidad auditable** para la generación de reportes.
- **Escalabilidad** para automatizar otros procesos del área MRM.

### Consideraciones Futuras:
- Integración con sistemas *core* de Bancolombia.
- Desarrollo de un dashboard web para monitoreo en tiempo real.
- Implementación de métricas y *analytics* avanzados.
- Incorporación de notificaciones push y SMS.

### Impacto Esperado:
- **Reducción del 80%** en tiempo de gestión manual.
- **0% de planes vencidos** sin notificación previa.
- **100% de trazabilidad** en las alertas enviadas.
- **Mejora significativa** en la satisfacción del equipo MRM.