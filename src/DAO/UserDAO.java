package DAO;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends MySQLConnection {

    public List<User> getAllUsers() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        String sql = "SELECT usu_id, usu_identificacion, usu_login, usu_pass, usu_nombre, usu_apellido, usu_email, usu_dir, usu_tel, usu_estado, per_id, img_firma FROM usuario";
        MySQLConnection con = new MySQLConnection();
        con.conectar();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsuId(rs.getInt("usu_id"));
                user.setUsuIdentificacion(rs.getString("usu_identificacion"));
                user.setUsuLogin(rs.getString("usu_login"));
                user.setUsuPass(rs.getString("usu_pass"));
                user.setUsuNombre(rs.getString("usu_nombre"));
                user.setUsuApellido(rs.getString("usu_apellido"));
                user.setUsuEmail(rs.getString("usu_email"));
                user.setUsuDir(rs.getString("usu_dir"));
                user.setUsuTel(rs.getString("usu_tel"));
                user.setUsuEstado(rs.getString("usu_estado"));
                user.setPerId(rs.getInt("per_id"));
                user.setImgFirma(rs.getString("img_firma"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            con.desconectar();
        }
        return users;
    }

    public boolean existeLogin(String login) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT COUNT(*) as total FROM usuario WHERE usu_login = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, login.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error verificando login existente: " + e.getMessage());
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

    public User validarLogin(String login, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "SELECT usu_id, usu_identificacion, usu_login, usu_pass, usu_nombre, usu_apellido, usu_email, usu_dir, usu_tel, usu_estado, per_id, img_firma FROM usuario WHERE usu_login = ? AND usu_pass = ? AND usu_estado = 'Activo'";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, login.trim());
            ps.setString(2, password.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsuId(rs.getInt("usu_id"));
                user.setUsuIdentificacion(rs.getString("usu_identificacion"));
                user.setUsuLogin(rs.getString("usu_login"));
                user.setUsuPass(rs.getString("usu_pass"));
                user.setUsuNombre(rs.getString("usu_nombre"));
                user.setUsuApellido(rs.getString("usu_apellido"));
                user.setUsuEmail(rs.getString("usu_email"));
                user.setUsuDir(rs.getString("usu_dir"));
                user.setUsuTel(rs.getString("usu_tel"));
                user.setUsuEstado(rs.getString("usu_estado"));
                user.setPerId(rs.getInt("per_id"));
                user.setImgFirma(rs.getString("img_firma"));
                return user;
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Error validando login: " + e.getMessage());
            return null;
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

    public boolean add(User user) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        con.conectar();
        String sql = "INSERT INTO usuario(usu_identificacion, usu_login, usu_pass, usu_nombre, usu_apellido, usu_email, usu_dir, usu_tel, usu_estado, per_id, img_firma) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsuIdentificacion());
            ps.setString(2, user.getUsuLogin());
            ps.setString(3, user.getUsuPass());
            ps.setString(4, user.getUsuNombre());
            ps.setString(5, user.getUsuApellido());
            ps.setString(6, user.getUsuEmail());
            ps.setString(7, user.getUsuDir());
            ps.setString(8, user.getUsuTel());
            ps.setString(9, user.getUsuEstado());
            ps.setInt(10, user.getPerId());
            ps.setString(11, user.getImgFirma());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            con.desconectar();
        }
    }

    public boolean update(User user) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE usuario SET usu_identificacion=?, usu_login=?, usu_pass=?, usu_nombre=?, usu_apellido=?, usu_email=?, usu_dir=?, usu_tel=?, usu_estado=?, per_id=?, img_firma=? WHERE usu_id=?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsuIdentificacion());
            ps.setString(2, user.getUsuLogin());
            ps.setString(3, user.getUsuPass());
            ps.setString(4, user.getUsuNombre());
            ps.setString(5, user.getUsuApellido());
            ps.setString(6, user.getUsuEmail());
            ps.setString(7, user.getUsuDir());
            ps.setString(8, user.getUsuTel());
            ps.setString(9, user.getUsuEstado());
            ps.setInt(10, user.getPerId());
            ps.setString(11, user.getImgFirma());
            ps.setInt(12, user.getUsuId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            con.desconectar();
        }
    }

    public boolean delete(int usuId) {
        PreparedStatement ps = null;
        MySQLConnection con = new MySQLConnection();
        String sql = "UPDATE usuario SET usu_estado = 'Inactivo' WHERE usu_id = ?";

        try {
            con.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuId);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al inactivar usuario: " + e.getMessage());
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
        String sql = "SELECT COALESCE(MAX(usu_id), 0) + 1 as proximo_id FROM usuario";

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
