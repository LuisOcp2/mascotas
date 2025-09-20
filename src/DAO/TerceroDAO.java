/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Tercero;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lmog2
 */
public class TerceroDAO extends MySQLConnection {

    public List<Tercero> getAllTerceros() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Tercero> Terceros = new ArrayList<>();
        String sql = "SELECT id,ti_descripcion,identificacion,representante_legal,razon_social,direccion,tel1,tel2,observaciones,estado FROM `terceros`";
        MySQLConnection con = new MySQLConnection();
        con.conectar();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tercero objTercero = new Tercero();
                objTercero.setId(rs.getInt("id"));
                objTercero.setTi(rs.getString("ti_descripcion"));
                objTercero.setIdentificacion(rs.getString("identificacion"));
                objTercero.setRepresentante_legal(rs.getString("representante_legal"));
                objTercero.setRazon_social(rs.getString("razon_social"));
                objTercero.setDireccion(rs.getString("direccion"));
                objTercero.setTel1(rs.getString("tel1"));
                objTercero.setTel2(rs.getString("tel2"));
                objTercero.setObservaciones(rs.getString("observaciones"));
                objTercero.setEstado(rs.getString("estado"));

                Terceros.add(objTercero);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            con.desconectar();
        }
        return Terceros;
    }

    public boolean existeId(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM terceros WHERE id = ?";

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
            return true; // Por seguridad, asumir que existe si hay error
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

    public boolean existeIdentificacion(String identificacion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM terceros WHERE identificacion = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, identificacion.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }

            return false;

        } catch (SQLException e) {
            System.err.println("Error verificando identificación existente: " + e.getMessage());
            return true; // Por seguridad, asumir que existe si hay error
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

    public boolean add(Tercero Terc) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        con.conectar();
        String sql = "INSERT INTO terceros(id,ti_descripcion,identificacion,representante_legal,razon_social,direccion,tel1,tel2,observaciones,estado) VALUES (?,?,?,?,?,?,?,?,?,?)";
        System.out.println(Terc);
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Terc.getId());
            ps.setString(2, Terc.getTi());
            ps.setString(3, Terc.getIdentificacion());
            ps.setString(4, Terc.getRepresentante_legal());
            ps.setString(5, Terc.getRazon_social());
            ps.setString(6, Terc.getDireccion());
            ps.setString(7, Terc.getTel1());
            ps.setString(8, Terc.getTel2());
            ps.setString(9, Terc.getObservaciones());
            ps.setString(10, Terc.getEstado());
            ps.execute();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            con.desconectar();
        }
    }

    public boolean update(Tercero Terc) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE terceros SET  ti_descripcion=?,identificacion=?,representante_legal=?,razon_social=?,direccion=?,tel1=?,tel2=?,observaciones=?,estado=? WHERE id=? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, Terc.getTi());
            ps.setString(2, Terc.getIdentificacion());
            ps.setString(3, Terc.getRepresentante_legal());
            ps.setString(4, Terc.getRazon_social());
            ps.setString(5, Terc.getDireccion());
            ps.setString(6, Terc.getTel1());
            ps.setString(7, Terc.getTel2());
            ps.setString(8, Terc.getObservaciones());
            ps.setString(9, Terc.getEstado());
            ps.setInt(10, Terc.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean delete(int id) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE terceros SET estado = 'Inactivo' WHERE id = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar tercero: " + e.getMessage());
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
    String sql = "SELECT COALESCE(MAX(id), 0) + 1 as proximo_id FROM terceros";
    
    try {
        con.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("proximo_id");
        }
        
        return 1; // Si no hay registros, empezar con 1
        
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
