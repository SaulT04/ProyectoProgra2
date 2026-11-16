# Propuesta Inicial de Proyecto – Programación Orientada a Objetos

**Carrera:** Ingeniería de Software  
**Materia:** Programación II  
**Periodo:** Segundo Parcial / Proyecto Final  
**Estudiante(s):** Saul Torres  
**Fecha de entrega:** 2026/11/19

---

## 1. Datos Generales del Proyecto

| Campo | Descripción |
|---|---|
| **Nombre del proyecto:** | ParkManager – Sistema Web de Gestión de Parqueos |
| **Tipo de aplicación:** | ☐ Escritorio ☑ Web ☐ Móvil ☐ Otro: — |
| **Lenguaje / entorno de desarrollo:** | Java 17, Apache Tomcat, Maven y Visual Studio Code. |
| **Repositorio Git (URL):** | Pendiente de creación. |
| **Uso de Inteligencia Artificial:** | ☐ No ☑ Sí (asistencia en planificación y desarrollo). |

**Si usas IA, explica brevemente cómo y en qué etapa contribuye:**

> Se utilizará inteligencia artificial como herramienta de apoyo durante la planificación, diseño de clases, identificación de errores y comprensión de conceptos de programación. Su participación será complementaria; el estudiante analizará, comprenderá, adaptará y defenderá el código implementado.

---

## 2. Descripción del Proyecto

### Resumen breve

ParkManager es una aplicación web desarrollada en Java que permite administrar las operaciones de un establecimiento de estacionamiento vehicular. Está dirigida a operadores de parqueos que necesitan controlar sus espacios y registrar los vehículos que ingresan y salen. El sistema permitirá gestionar la disponibilidad de espacios, registrar las entradas y salidas de vehículos y calcular automáticamente las tarifas correspondientes. También contará con un historial de operaciones y estadísticas de ocupación e ingresos. Utilizará PostgreSQL para almacenar la información de manera permanente. Su desarrollo aplicará Programación Orientada a Objetos y principios de diseño de software.

### Objetivos principales

1. **Administrar los espacios de estacionamiento:** desarrollar un módulo que permita registrar, consultar y controlar la disponibilidad de los espacios, diferenciándolos según el tipo de vehículo que pueden recibir.

2. **Gestionar el ingreso y salida de vehículos:** implementar un módulo que permita registrar vehículos, asignarles espacios disponibles, registrar sus horarios de entrada y salida y calcular automáticamente el costo de estacionamiento.

3. **Consultar el historial del parqueo:** desarrollar un módulo que permita consultar las estadías anteriores, buscar vehículos mediante su placa y visualizar información sobre sus fechas de ingreso, salida, tiempo de permanencia y monto pagado.

---

## 3. Diseño Técnico y Aplicación de POO

### Principios de POO aplicados

Marca los que planeas usar:

- [x] Encapsulamiento (atributos privados y métodos públicos).
- [x] Uso de constructores.
- [x] Herencia.
- [x] Polimorfismo.
- [x] Interfaces o clases abstractas.

**Otros principios de diseño que se aplicarán:**

- Cohesión y responsabilidad única (SRP).
- Bajo acoplamiento.
- Modularidad.
- Reutilización de código.
- Extensibilidad.
- Portabilidad.
- Programación defensiva.
- Mantenibilidad.
- Testabilidad.
- Simplicidad.

### Persistencia de datos

- [ ] Archivos locales.
- [x] Base de datos (PostgreSQL).
- [ ] En memoria (temporal).
- [ ] Otro: No aplica.

**Tecnologías complementarias:**

- Java JDBC para la conexión y comunicación con PostgreSQL.
- HTML y CSS para la interfaz web.
- Jakarta Servlets y JSP para gestionar las solicitudes y presentar información.
- Apache Tomcat como servidor web.
- Maven para la administración de dependencias.

**Arquitectura propuesta:** arquitectura monolítica modular organizada en capas: presentación, lógica de negocio, acceso a datos y base de datos.

---

## 4. Funcionalidades Principales

| Nº | Nombre de la funcionalidad | Descripción breve | Estado actual |
|---|---|---|---|
| 1 | Administración de espacios | Permite registrar espacios de estacionamiento, consultar su disponibilidad y controlar cuáles están ocupados o libres. | ☑ Planeada ☐ En desarrollo |
| 2 | Gestión de ingreso y salida de vehículos | Permite registrar vehículos, asignar espacios, controlar horarios de entrada y salida y calcular las tarifas de estacionamiento. | ☑ Planeada ☐ En desarrollo |
| 3 | Consulta del historial de vehículos | Permite consultar las estadías anteriores y buscar vehículos por su placa, mostrando sus fechas de ingreso, salida, tiempo de permanencia y monto pagado. | ☑ Planeada ☐ En desarrollo |

---

## 5. Compromiso del Estudiante

Declaro que:

- Entiendo los criterios de evaluación establecidos en las rúbricas.
- Presentaré una demostración funcional del proyecto.
- Defenderé el código que yo mismo implementé y explicaré las clases y métodos principales.
- Si usé herramientas de IA, comprendo su funcionamiento y las adapté al contexto del proyecto.

**Firma (nombre completo):** Saul Torres

---

## 6. Validación del Docente (completa el profesor)

| Campo | Detalle |
|---|---|
| **Visto bueno del docente:** | ☐ Aprobado para desarrollar ☐ Requiere ajustes ☐ Rechazado |
| **Comentarios / Observaciones:** | |
| **Firma docente:** | |
| **Fecha de revisión:** | |

---

> **Instrucciones para entrega:**
> - Guarda este archivo como `README.md` dentro de tu repositorio Git.
> - Completa todas las secciones antes de tu presentación inicial.
> - No borres las casillas ni el formato para garantizar uniformidad del curso.
> - El docente revisará y aprobará esta propuesta antes del desarrollo completo.

---