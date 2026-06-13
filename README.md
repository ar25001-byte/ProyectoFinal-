# Sistema de Inventario para Tienda de PC

## Descripción

Sistema de escritorio desarrollado en Java Swing para administrar el inventario de una tienda de computadoras.

El proyecto fue elaborado aplicando las prácticas observadas en los videos del docente, utilizando una arquitectura organizada por capas y una base de datos SQL Server para el almacenamiento de información.

---

## Funcionalidades

- Registrar productos.
- Consultar productos.
- Buscar productos.
- Modificar productos.
- Eliminar productos.
- Validar información.
- Mostrar datos mediante JTable.

---

## Tecnologías Utilizadas

### Java 17

Lenguaje principal utilizado para el desarrollo.

### Swing

Framework utilizado para construir la interfaz gráfica.

### SQL Server

Motor de base de datos utilizado para almacenar la información.

### JDBC

Tecnología utilizada para conectar Java con SQL Server.

### Maven

Herramienta utilizada para administrar dependencias.

### GitHub

Utilizado para control de versiones.

---

## Estructura del Proyecto

src/main/java/com/tiendapc

- modelo
- persistencia
- controlador
- vista
- utils

---

## Componentes Principales

### Producto.java

Representa la entidad principal del sistema.

### ConnectionManager.java

Gestiona la conexión con SQL Server mediante JDBC.

### ProductoDAO.java

Contiene las operaciones CRUD.

### InventarioForm.java

Interfaz gráfica desarrollada con Swing.

### Main.java

Punto de inicio de la aplicación.

---

## Base de Datos

### Nombre

TiendaPCDB

### Tabla Principal

Productos

Campos:

- Id
- Codigo
- Nombre
- Categoria
- Precio
- Stock

---

## Buenas Prácticas Aplicadas

- Arquitectura en capas.
- Programación Orientada a Objetos.
- Uso de DAO.
- Uso de PreparedStatement.
- Manejo de excepciones.
- Validación de formularios.
- Uso de JTable y JScrollPane.
- Uso de JOptionPane.

---

## Autor

Proyecto académico desarrollado para la asignatura Desarrollo de Interfaces Gráficas utilizando Java Swing.