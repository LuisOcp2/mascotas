package DAO;

import Model.FormaPago;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormaPagoDAO extends MySQLConnection {

    public List<FormaPago> getAllFormasPago() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FormaPago> formasPago = new ArrayList<>();
        String sql = "SELECT fp_id, fp_descripcion, fp_estado FROM forma_pago";
        MySQLConnection con = new MySQLConnection();

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                FormaPago formaPago = new FormaPago();
                formaPago.setFpId(rs.getInt("fp_id"));
                formaPago.setFpDescripcion(rs.getString("fp_descripcion"));
                formaPago.setFpEstado(rs.getString("fp_estado"));
                formasPago.add(formaPago);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener formas de pago: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
        return formasPago;
    }

    public boolean existeId(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM forma_pago WHERE fp_id = ?";

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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
        String sql = "SELECT COUNT(*) as total FROM forma_pago WHERE fp_descripcion = ?";

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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean add(FormaPago formaPago) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "INSERT INTO forma_pago(fp_id, fp_descripcion, fp_estado) VALUES (?,?,?)";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, formaPago.getFpId());
            ps.setString(2, formaPago.getFpDescripcion());
            ps.setString(3, formaPago.getFpEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar forma de pago: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean update(FormaPago formaPago) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE forma_pago SET fp_descripcion=?, fp_estado=? WHERE fp_id=?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, formaPago.getFpDescripcion());
            ps.setString(2, formaPago.getFpEstado());
            ps.setInt(3, formaPago.getFpId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar forma de pago: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.desconectar();
            } catch (SQLException e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }

    public boolean delete(int id) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE forma_pago SET fp_estado = 'Inactivo' WHERE fp_id = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar forma de pago: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
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
        String sql = "SELECT COALESCE(MAX(fp_id), 0) + 1 as proximo_id FROM forma_pago";

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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                con.desconectar();
            } catch (Exception e) {
                System.err.println("Error cerrando conexión: " + e.getMessage());
            }
        }
    }
}
