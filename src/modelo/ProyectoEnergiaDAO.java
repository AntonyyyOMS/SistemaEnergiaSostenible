package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoEnergiaDAO implements IProyectoEnergiaDAO {
    
    private Connection obtenerConexion() throws SQLException {
        return ConexionBD.getInstance().getConnection();
    }

    @Override
    public int crearProyectoEnergia(ProyectoEnergia proyecto) {
        int idGenerado = -1;
        String sql = "{CALL sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = obtenerConexion();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, "CREATE");
            cstmt.setNull(2, Types.INTEGER);
            cstmt.setString(3, proyecto.getNombreProyecto());
            cstmt.setString(4, proyecto.getTipoFuente());
            cstmt.setDouble(5, proyecto.getCapacidadMW());
            cstmt.registerOutParameter(6, Types.INTEGER);

            cstmt.execute();
            idGenerado = cstmt.getInt(6);

        } catch (SQLException e) {
            System.err.println("Error al crear proyecto de energía: " + e.getMessage());
            e.printStackTrace();
        }
        return idGenerado;
    }

    @Override
    public ProyectoEnergia leerProyectoEnergia(int id) {
        ProyectoEnergia proyecto = null;
        String sql = "{CALL sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = obtenerConexion();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, "READ");
            cstmt.setInt(2, id);
            cstmt.setNull(3, Types.VARCHAR);
            cstmt.setNull(4, Types.VARCHAR);
            cstmt.setNull(5, Types.FLOAT);
            cstmt.registerOutParameter(6, Types.INTEGER);

            try (ResultSet rs = cstmt.executeQuery()) {
                if (rs.next()) {
                    proyecto = new ProyectoEnergia(
                        rs.getInt("id"),
                        rs.getString("nombreProyecto"),
                        rs.getString("tipoFuente"),
                        rs.getDouble("capacidadMW")
                    );
                }
            }
            cstmt.getInt(6);

        } catch (SQLException e) {
            System.err.println("Error al leer proyecto de energía: " + e.getMessage());
            e.printStackTrace();
        }
        return proyecto;
    }

    @Override
    public List<ProyectoEnergia> leerTodosProyectosEnergia() {
        List<ProyectoEnergia> proyectos = new ArrayList<>();
        String sql = "{CALL sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = obtenerConexion();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, "READ_ALL");
            cstmt.setNull(2, Types.INTEGER);
            cstmt.setNull(3, Types.VARCHAR);
            cstmt.setNull(4, Types.VARCHAR);
            cstmt.setNull(5, Types.FLOAT);
            cstmt.registerOutParameter(6, Types.INTEGER);

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    ProyectoEnergia proyecto = new ProyectoEnergia(
                        rs.getInt("id"),
                        rs.getString("nombreProyecto"),
                        rs.getString("tipoFuente"),
                        rs.getDouble("capacidadMW")
                    );
                    proyectos.add(proyecto);
                }
            }
            cstmt.getInt(6);

        } catch (SQLException e) {
            System.err.println("Error al leer todos los proyectos de energía: " + e.getMessage());
            e.printStackTrace();
        }
        return proyectos;
    }

    @Override
    public boolean actualizarProyectoEnergia(ProyectoEnergia proyecto) {
        boolean exito = false;
        String sql = "{CALL sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = obtenerConexion();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, "UPDATE");
            cstmt.setInt(2, proyecto.getId());
            cstmt.setString(3, proyecto.getNombreProyecto());
            cstmt.setString(4, proyecto.getTipoFuente());
            cstmt.setDouble(5, proyecto.getCapacidadMW());
            cstmt.registerOutParameter(6, Types.INTEGER);

            cstmt.execute();
            int filasAfectadas = cstmt.getInt(6);
            exito = (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al actualizar proyecto de energía: " + e.getMessage());
            e.printStackTrace();
        }
        return exito;
    }

    @Override
    public boolean eliminarProyectoEnergia(int id) {
        boolean exito = false;
        String sql = "{CALL sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = obtenerConexion();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, "DELETE");
            cstmt.setInt(2, id);
            cstmt.setNull(3, Types.VARCHAR);
            cstmt.setNull(4, Types.VARCHAR);
            cstmt.setNull(5, Types.FLOAT);
            cstmt.registerOutParameter(6, Types.INTEGER);

            cstmt.execute();
            int filasAfectadas = cstmt.getInt(6);
            exito = (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("Error al eliminar proyecto de energía: " + e.getMessage());
            e.printStackTrace();
        }
        return exito;
    }
}