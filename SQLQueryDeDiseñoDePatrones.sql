CREATE DATABASE SistemaGestionEnergiaDB;
GO

USE SistemaGestionEnergiaDB;
GO

CREATE TABLE ProyectoEnergia (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombreProyecto VARCHAR(100),
    tipoFuente VARCHAR(50),
    capacidadMW FLOAT
);
GO

USE SistemaGestionEnergiaDB;
GO

CREATE PROCEDURE sp_crud_proyectoEnergia
    @opcion VARCHAR(10),
    @id INT = NULL,
    @nombreProyecto VARCHAR(100) = NULL,
    @tipoFuente VARCHAR(50) = NULL,
    @capacidadMW FLOAT = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        INSERT INTO ProyectoEnergia (nombreProyecto, tipoFuente, capacidadMW)
        VALUES (@nombreProyecto, @tipoFuente, @capacidadMW);
        SET @resultado = SCOPE_IDENTITY();
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        SELECT id, nombreProyecto, tipoFuente, capacidadMW 
        FROM ProyectoEnergia 
        WHERE id = @id;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE ProyectoEnergia
        SET nombreProyecto = @nombreProyecto,
            tipoFuente = @tipoFuente,
            capacidadMW = @capacidadMW
        WHERE id = @id;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        DELETE FROM ProyectoEnergia
        WHERE id = @id;
    END
END;
GO

﻿USE [SistemaGestionEnergiaDB]
GO
/****** Object:  Table [dbo].[ProyectoEnergia]    Script Date: 26/05/2025 15:48:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProyectoEnergia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombreProyecto] [varchar](100) NULL,
	[tipoFuente] [varchar](50) NULL,
	[capacidadMW] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[sp_crud_proyectoEnergia]    Script Date: 26/05/2025 15:48:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_crud_proyectoEnergia]
    @opcion VARCHAR(10),
    @id INT = NULL,
    @nombreProyecto VARCHAR(100) = NULL,
    @tipoFuente VARCHAR(50) = NULL,
    @capacidadMW FLOAT = NULL,
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    IF @opcion = 'CREATE'
    BEGIN
        INSERT INTO ProyectoEnergia (nombreProyecto, tipoFuente, capacidadMW)
        VALUES (@nombreProyecto, @tipoFuente, @capacidadMW);
        SET @resultado = SCOPE_IDENTITY();
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        SELECT id, nombreProyecto, tipoFuente, capacidadMW 
        FROM ProyectoEnergia 
        WHERE id = @id;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE ProyectoEnergia
        SET nombreProyecto = @nombreProyecto,
            tipoFuente = @tipoFuente,
            capacidadMW = @capacidadMW
        WHERE id = @id;
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        DELETE FROM ProyectoEnergia
        WHERE id = @id;
    END
END;
GO