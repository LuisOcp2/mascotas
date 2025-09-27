package DAO;

import Model.Concepto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConceptoDAO extends MySQLConnection {

    public List<Concepto> getAllConceptos() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Concepto> conceptos = new ArrayList<>();
        String sql = "SELECT con_id, con_descripcion, con_estado FROM concepto";
        MySQLConnection con = new MySQLConnection();
        
        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Concepto concepto = new Concepto();
                concepto.setConId(rs.getInt("con_id"));
                concepto.setConDescripcion(rs.getString("con_descripcion"));
                concepto.setConEstado(rs.getString("con_estado"));
                conceptos.add(concepto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conceptos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
        return conceptos;
    }

    public boolean existeId(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM concepto WHERE con_id = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error verificando ID existente: " + e.getMessage());
            return true;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean existeDescripcion(String descripcion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM concepto WHERE LOWER(TRIM(con_descripcion)) = LOWER(TRIM(?))";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, descripcion.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error verificando descripción existente: " + e.getMessage());
            return true;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean existeDescripcionParaOtroId(String descripcion, int idExcluir) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM concepto WHERE LOWER(TRIM(con_descripcion)) = LOWER(TRIM(?)) AND con_id != ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, descripcion.trim());
            ps.setInt(2, idExcluir);
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error verificando descripción duplicada para actualización: " + e.getMessage());
            return true;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean add(Concepto concepto) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "INSERT INTO concepto(con_id, con_descripcion, con_estado) VALUES (?,?,?)";
        
        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, concepto.getConId());
            ps.setString(2, concepto.getConDescripcion());
            ps.setString(3, concepto.getConEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar concepto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean update(Concepto concepto) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE concepto SET con_descripcion=?, con_estado=? WHERE con_id=?";
        
        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, concepto.getConDescripcion());
            ps.setString(2, concepto.getConEstado());
            ps.setInt(3, concepto.getConId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar concepto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean delete(int id) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE concepto SET con_estado = 'Inactivo' WHERE con_id = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar concepto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public int sugerirProximoId() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COALESCE(MAX(con_id), 0) + 1 as proximo_id FROM concepto";
        
        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("proximo_id");
            }
            return 1;
            
        } catch (Exception e) {
            System.err.println("Error obteniendo próximo ID: " + e.getMessage());
            return 1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                con.desconectar();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}