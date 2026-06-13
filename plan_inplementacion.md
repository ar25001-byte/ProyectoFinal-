# Plan de Implementación

## Proyecto

Sistema de Inventario para Tienda de PC

---

# Fase 1: Análisis y Requerimientos

## Objetivo

Definir las necesidades del sistema y establecer los requerimientos funcionales y no funcionales.

## Actividades

- Análisis de los videos del docente.
- Definición de funcionalidades.
- Selección de tecnologías.
- Elaboración de documentación.

---

# Fase 2: Diseño de la Arquitectura

## Objetivo

Diseñar la estructura general del proyecto.

## Actividades

- Creación de paquetes.
- Diseño de clases.
- Diseño de base de datos.
- Diseño de interfaz gráfica.

---

# Fase 3: Implementación de la Base de Datos

## Objetivo

Crear la estructura de almacenamiento de información.

## Actividades

- Crear la base de datos TiendaPCDB.
- Crear la tabla Productos.
- Configurar SQL Server.
- Verificar la conexión mediante JDBC.

---

# Fase 4: Desarrollo de la Persistencia

## Objetivo

Implementar el acceso a datos.

## Actividades

### ConnectionManager

Desarrollar la clase encargada de gestionar la conexión con SQL Server.

### ProductoDAO

Implementar las operaciones:

- Insertar
- Consultar
- Buscar
- Actualizar
- Eliminar

Utilizando PreparedStatement.

---

# Fase 5: Desarrollo de la Interfaz Gráfica

## Objetivo

Construir la interfaz de usuario.

## Actividades

- Crear formularios Swing.
- Implementar JTable.
- Implementar botones de acción.
- Implementar cuadros de diálogo con JOptionPane.

---

# Fase 6: Integración del Sistema

## Objetivo

Conectar la interfaz con la lógica de negocio y la base de datos.

## Actividades

- Integrar DAO.
- Integrar formularios.
- Validar flujo de información.

---

# Fase 7: Pruebas

## Objetivo

Verificar el correcto funcionamiento del sistema.

## Actividades

- Pruebas de inserción.
- Pruebas de búsqueda.
- Pruebas de actualización.
- Pruebas de eliminación.
- Pruebas de conexión.

---

# Fase 8: Control de Versiones

## Objetivo

Mantener un historial organizado del desarrollo.

## Actividades

- Crear repositorio GitHub.
- Crear rama main.
- Crear rama develop.
- Registrar commits.
- Realizar Pull Requests.

---

# Fase 9: Documentación y Entrega

## Objetivo

Preparar la entrega final del proyecto.

## Actividades

- Elaborar README.
- Elaborar documentación técnica.
- Verificar funcionamiento final.
- Preparar presentación.

---

## Resultado Esperado

Una aplicación de escritorio completamente funcional para la administración de inventario de una tienda de PC, desarrollada con Java Swing y SQL Server, siguiendo las buenas prácticas presentadas en los videos del docente.