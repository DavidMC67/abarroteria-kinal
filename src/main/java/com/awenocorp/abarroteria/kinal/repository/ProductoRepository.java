/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.awenocorp.abarroteria.kinal.repository;

import javafx.collections.ObservableList;
import main.java.com.awenocorp.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.awenocorp.abarroteria.kinal.model.Producto;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;

/**
 *
 * @author informatica
 */
public class ProductoRepository {
    public ObservableList<Producto> findAll(){
        String sql = "select*from productos";
        try(PreparedStatement pstm = DataBaseConnection.getDataBaseConnection(sql)){
            ResultSet rs = pstm.executeQuery();
            ObservableList<Producto> lista = FXCollections.ObservableArrayList();
            if(rs.next()){
                lista.add(new Producto(
                     rs.getString("id_productos"),
                     rs.getString(""),
                     rs.getInt(""),
                     rs.getBigDecimal("precio")
                     ));
            }
            return lista;
        }catch(SQLException e){
            throw RuntimeException("error en la consulta");
        }
        return null;
    }
}
