-- ============================================================
-- Script SQL para crear la base de datos TiendaPCDB
-- y la tabla Productos.
-- Compatible con SQL Server 2019+
-- ============================================================

-- 1. Crear la base de datos si no existe
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'TiendaPCDB')
BEGIN
    CREATE DATABASE TiendaPCDB;
    PRINT 'Base de datos TiendaPCDB creada exitosamente.';
END
ELSE
BEGIN
    PRINT 'La base de datos TiendaPCDB ya existe.';
END
GO

-- 2. Usar la base de datos
USE TiendaPCDB;
GO

-- 3. Crear la tabla Productos si no existe
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.Productos') AND type = 'U')
BEGIN
    CREATE TABLE dbo.Productos (
        Id        INT           IDENTITY(1,1) PRIMARY KEY,  -- Clave primaria autoincremental
        Codigo    VARCHAR(20)   NOT NULL UNIQUE,             -- Código único del producto
        Nombre    VARCHAR(100)  NOT NULL,                    -- Nombre del producto
        Categoria VARCHAR(50)   NOT NULL,                    -- Categoría (ej: Procesador, RAM, etc.)
        Precio    DECIMAL(10,2) NOT NULL,                    -- Precio unitario
        Stock     INT           NOT NULL                     -- Cantidad en inventario
    );
    PRINT 'Tabla Productos creada exitosamente.';
END
ELSE
BEGIN
    PRINT 'La tabla Productos ya existe.';
END
GO

-- 4. (Opcional) Insertar datos de prueba
INSERT INTO dbo.Productos (Codigo, Nombre, Categoria, Precio, Stock) VALUES
    ('CPU-001', 'Intel Core i7-13700K',          'Procesador',       389.99,  15),
    ('CPU-002', 'AMD Ryzen 9 7900X',             'Procesador',       449.00,  10),
    ('RAM-001', 'Corsair Vengeance 16GB DDR5',   'Memoria RAM',      89.99,   30),
    ('GPU-001', 'NVIDIA GeForce RTX 4070',       'Tarjeta Gráfica',  599.99,   8),
    ('SSD-001', 'Samsung 980 PRO 1TB NVMe',      'Almacenamiento',   109.99,  25),
    ('MBD-001', 'ASUS ROG Strix B650-A',         'Tarjeta Madre',    229.99,  12),
    ('PSU-001', 'EVGA SuperNOVA 850W 80+ Gold',  'Fuente de Poder',  129.99,  20),
    ('CAS-001', 'NZXT H510 Flow',                'Gabinete',          89.99,  18);

PRINT 'Datos de prueba insertados correctamente.';
GO

-- 5. Crear la tabla Usuarios si no existe
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.Usuarios') AND type = 'U')
BEGIN
    CREATE TABLE dbo.Usuarios (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Usuario VARCHAR(50) NOT NULL UNIQUE,
        Password VARCHAR(255) NOT NULL
    );
    PRINT 'Tabla Usuarios creada exitosamente.';
    
    -- Insertar usuario de prueba
    INSERT INTO dbo.Usuarios(Usuario, Password) VALUES ('admin', 'admin123');
    PRINT 'Usuario de prueba (admin) insertado correctamente.';
END
ELSE
BEGIN
    PRINT 'La tabla Usuarios ya existe.';
END
GO
