# Requerimientos del Proyecto

## Nombre del Proyecto

Sistema de Inventario para Tienda de PC

## Introducción

El presente proyecto tiene como finalidad desarrollar una aplicación de escritorio utilizando Java Swing para la gestión de inventario de una tienda especializada en productos y componentes para computadoras.

La aplicación permitirá registrar, consultar, actualizar y eliminar productos almacenados en una base de datos SQL Server, aplicando los conceptos de Programación Orientada a Objetos, acceso a datos mediante JDBC y organización por capas presentados en los videos del docente.

---

## Objetivo General

Desarrollar una aplicación de escritorio funcional que permita administrar eficientemente el inventario de una tienda de productos informáticos.

---

## Objetivos Específicos

- Crear una interfaz gráfica amigable utilizando Java Swing.
- Implementar una conexión segura con SQL Server mediante JDBC.
- Aplicar Programación Orientada a Objetos.
- Implementar operaciones CRUD para la gestión de productos.
- Validar la información ingresada por el usuario.
- Organizar el código siguiendo una arquitectura en capas.

---

## Requerimientos Funcionales

### RF-01 Registro de Productos

El sistema deberá permitir registrar nuevos productos en la base de datos.

### RF-02 Consulta de Productos

El sistema deberá mostrar todos los productos registrados mediante una JTable.

### RF-03 Búsqueda de Productos

El sistema deberá permitir realizar búsquedas por código o nombre.

### RF-04 Actualización de Productos

El usuario podrá modificar la información de productos existentes.

### RF-05 Eliminación de Productos

El sistema deberá permitir eliminar productos seleccionados.

### RF-06 Validación de Datos

El sistema validará que los campos obligatorios no se encuentren vacíos.

### RF-07 Validación Numérica

El sistema verificará que el precio y la cantidad sean valores numéricos válidos.

### RF-08 Persistencia de Información

Los datos deberán almacenarse permanentemente en SQL Server.

### RF-09 Mensajes Informativos

El sistema deberá utilizar JOptionPane para informar resultados, errores y advertencias.

---

## Requerimientos No Funcionales

### RNF-01 Rendimiento

La aplicación deberá responder rápidamente a las acciones del usuario.

### RNF-02 Usabilidad

La interfaz deberá ser intuitiva y fácil de utilizar.

### RNF-03 Seguridad

Las consultas SQL deberán utilizar PreparedStatement.

### RNF-04 Mantenibilidad

El código deberá estar organizado por paquetes y documentado.

### RNF-05 Compatibilidad

El sistema deberá funcionar con Java 17 o superior.

---

## Tecnologías Requeridas

- Java 17
- Java Swing
- SQL Server
- JDBC
- Maven
- GitHub

---

## Arquitectura Requerida

El proyecto deberá organizarse utilizando los siguientes paquetes:

- modelo
- persistencia
- controlador
- vista
- utils

Siguiendo las recomendaciones observadas en los videos proporcionados por el docente.