/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.awenocorp.abarroteria.kinal.repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.awenocorp.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.awenocorp.abarroteria.kinal.model.Producto;

public class ProductoRepository {
    public ObservableList<Producto> findAll(){
        String sql = "SELECT * FROM productos;";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Producto> lista = FXCollections.observableArrayList();

            while(rs.next()){
                lista.add(new Producto(
                    rs.getString("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getInt("stock"),
                    rs.getBigDecimal("precio")        
                ));
            }
            return lista;

        } catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error en la consulta.");
        }
    }

    // Nuevo método para eliminar en la base de datos
    public void delete(String idProducto) {
        String sqlDetalles = "DELETE FROM detalles_facturas WHERE id_producto = ?;";
        String sqlProducto = "DELETE FROM productos WHERE id_producto = ?;";

        Connection conn = null;
        try {
            conn = DataBaseConnection.getDataBaseConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmDetalles = conn.prepareStatement(sqlDetalles)) {
                pstmDetalles.setString(1, idProducto);
                pstmDetalles.executeUpdate();
            }

            try (PreparedStatement pstmProducto = conn.prepareStatement(sqlProducto)) {
                pstmProducto.setString(1, idProducto);
                pstmProducto.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignorar */ }
            }
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignorar */ }
            }
        }
    }
}