package modelo;

import java.math.BigDecimal; // Para el tipo DECIMAL/NUMERIC de SQL Server
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date; // Para java.sql.Date, que es compatible con el tipo DATE de SQL Server
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; // Para java.sql.Types
import java.time.LocalDate; // Para manejar fechas en Java (Modelo RegistroConsumo usa LocalDate)
import java.util.ArrayList;
import java.util.List;

public class RegistroConsumoDAO {

    public RegistroConsumoDAO() {
        // Constructor vacío
    }

    private Connection obtenerConexion() {
        return ConexionBD.getInstance().getConnection();
    }

    public int crearRegistroConsumo(RegistroConsumo registro) {
        int idGenerado = -1;
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = obtenerConexion(); // Usa el método auxiliar para obtener la conexión
            if (connection == null) {
                System.err.println("La conexión a la base de datos es nula. No se puede crear registro.");
                return idGenerado;
            }

            // La llamada al SP con 8 parámetros (7 de entrada + 1 de salida)
            callableStatement = connection.prepareCall("{CALL sp_crud_registroConsumo(?, ?, ?, ?, ?, ?, ?, ?)}");

            // 1. @opcion
            callableStatement.setString(1, "CREATE");

            // 2. @id (NULL para CREATE ya que es autoincremental en la BD)
            callableStatement.setObject(2, null); 

            // 3. @fecha (java.time.LocalDate a java.sql.Date)
            if (registro.getFecha() != null) {
                callableStatement.setDate(3, Date.valueOf(registro.getFecha()));
            } else {
                callableStatement.setNull(3, Types.DATE); // Si la fecha es opcional y viene nula
            }

            callableStatement.setString(4, registro.getTipo());

            callableStatement.setDouble(5, registro.getConsumo());

            callableStatement.setString(6, registro.getUnidad());

            callableStatement.setBigDecimal(7, registro.getCosto());

            callableStatement.registerOutParameter(8, Types.INTEGER);

            callableStatement.execute();

            idGenerado = callableStatement.getInt(8);

        } catch (SQLException e) {
            System.err.println("Error al crear registro de consumo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idGenerado;
    }


    public RegistroConsumo leerRegistroConsumo(int id) {
        RegistroConsumo registro = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = obtenerConexion();
            if (connection == null) {
                System.err.println("La conexión a la base de datos es nula. No se puede leer registro.");
                return null;
            }

            callableStatement = connection.prepareCall("{CALL sp_crud_registroConsumo(?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, "READ"); // Opción para leer un solo registro
            callableStatement.setInt(2, id);        // @id
            callableStatement.setObject(3, null);   // @fecha
            callableStatement.setObject(4, null);   // @tipo
            callableStatement.setObject(5, null);   // @consumo
            callableStatement.setObject(6, null);   // @unidad
            callableStatement.setObject(7, null);   // @costo
            callableStatement.registerOutParameter(8, Types.INTEGER); // @resultado

            boolean hasResultSet = callableStatement.execute();

            if (hasResultSet) {
                resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    registro = new RegistroConsumo(
                        resultSet.getInt("id"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("tipo"),
                        resultSet.getDouble("consumo"),
                        resultSet.getString("unidad"),
                        resultSet.getBigDecimal("costo")
                    );
                }
            }
            // int resultado = callableStatement.getInt(8); // Puedes obtener @resultado si lo necesitas

        } catch (SQLException e) {
            System.err.println("Error al leer registro de consumo por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registro;
    }
    public List<RegistroConsumo> leerTodosRegistrosConsumo() {
        List<RegistroConsumo> registros = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = obtenerConexion();
            if (connection == null) {
                System.err.println("La conexión a la base de datos es nula. No se pueden leer registros.");
                return registros;
            }

            callableStatement = connection.prepareCall("{CALL sp_crud_registroConsumo(?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, "READ_ALL"); // Opción para leer todos los registros
            callableStatement.setObject(2, null);   // @id
            callableStatement.setObject(3, null);   // @fecha
            callableStatement.setObject(4, null);   // @tipo
            callableStatement.setObject(5, null);   // @consumo
            callableStatement.setObject(6, null);   // @unidad
            callableStatement.setObject(7, null);   // @costo (NO ES OUTPUT, se lee del ResultSet)
            callableStatement.registerOutParameter(8, Types.INTEGER); // @resultado

            boolean hasResultSet = callableStatement.execute();

            if (hasResultSet) {
                resultSet = callableStatement.getResultSet();
                while (resultSet.next()) {
                    registros.add(new RegistroConsumo(
                        resultSet.getInt("id"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("tipo"),
                        resultSet.getDouble("consumo"),
                        resultSet.getString("unidad"),
                        resultSet.getBigDecimal("costo")
                    ));
                }
            }
            // int resultado = callableStatement.getInt(8); // Puedes obtener @resultado si lo necesitas

        } catch (SQLException e) {
            System.err.println("Error al leer todos los registros de consumo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registros;
    }

    public boolean actualizarRegistroConsumo(RegistroConsumo registro) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean exito = false;
        try {
            connection = obtenerConexion();
            if (connection == null) {
                System.err.println("La conexión a la base de datos es nula. No se puede actualizar registro.");
                return false;
            }

            callableStatement = connection.prepareCall("{CALL sp_crud_registroConsumo(?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, "UPDATE");
            callableStatement.setInt(2, registro.getId()); // ID del registro a actualizar
            
            if (registro.getFecha() != null) {
                callableStatement.setDate(3, Date.valueOf(registro.getFecha()));
            } else {
                callableStatement.setNull(3, Types.DATE);
            }
            callableStatement.setString(4, registro.getTipo());
            callableStatement.setDouble(5, registro.getConsumo());
            callableStatement.setString(6, registro.getUnidad());
            callableStatement.setBigDecimal(7, registro.getCosto());
            callableStatement.registerOutParameter(8, Types.INTEGER); // @resultado

            callableStatement.execute();

            int resultado = callableStatement.getInt(8);
            exito = (resultado > 0); // Si resultado > 0, significa que se actualizó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error al actualizar registro de consumo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }


    public boolean eliminarRegistroConsumo(int id) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        boolean exito = false;
        try {
            connection = obtenerConexion();
            if (connection == null) {
                System.err.println("La conexión a la base de datos es nula. No se puede eliminar registro.");
                return false;
            }

            callableStatement = connection.prepareCall("{CALL sp_crud_registroConsumo(?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, "DELETE");
            callableStatement.setInt(2, id); // ID del registro a eliminar
            callableStatement.setObject(3, null); // fecha
            callableStatement.setObject(4, null); // tipo
            callableStatement.setObject(5, null); // consumo
            callableStatement.setObject(6, null); // unidad
            callableStatement.setObject(7, null); // costo
            callableStatement.registerOutParameter(8, Types.INTEGER); // @resultado

            callableStatement.execute();

            int resultado = callableStatement.getInt(8);
            exito = (resultado > 0); // Si resultado > 0, significa que se eliminó al menos una fila

        } catch (SQLException e) {
            System.err.println("Error al eliminar registro de consumo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
}
