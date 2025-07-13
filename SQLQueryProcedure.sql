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
        SET @resultado = SCOPE_IDENTITY(); -- Devuelve el ID generado
    END
    ELSE IF @opcion = 'READ'
    BEGIN
        -- SELECT aquí para que el DAO pueda leer los datos
        SELECT id, nombreProyecto, tipoFuente, capacidadMW
        FROM ProyectoEnergia
        WHERE id = @id;
        -- Para READ, @resultado no es directamente el ID, sino si se encontró o no (ej. 1 si se encontró, 0 si no)
        -- En el DAO, la lectura se maneja con ResultSet, el @resultado del SP puede ser ignorado para READ.
        -- Podrías devolver @@ROWCOUNT en @resultado si quisieras saber si encontró una fila.
        IF @@ROWCOUNT > 0 SET @resultado = 1; ELSE SET @resultado = 0;
    END
    ELSE IF @opcion = 'UPDATE'
    BEGIN
        UPDATE ProyectoEnergia
        SET
            nombreProyecto = ISNULL(@nombreProyecto, nombreProyecto), -- Actualiza solo si no es NULL
            tipoFuente = ISNULL(@tipoFuente, tipoFuente),             -- Actualiza solo si no es NULL
            capacidadMW = ISNULL(@capacidadMW, capacidadMW)           -- Actualiza solo si no es NULL
        WHERE id = @id;
        SET @resultado = @@ROWCOUNT; -- Devuelve el número de filas afectadas
    END
    ELSE IF @opcion = 'DELETE'
    BEGIN
        DELETE FROM ProyectoEnergia
        WHERE id = @id;
        SET @resultado = @@ROWCOUNT; -- Devuelve el número de filas afectadas
    END
    ELSE
    BEGIN
        SET @resultado = -1; -- Opción no válida
    END
END;
GO