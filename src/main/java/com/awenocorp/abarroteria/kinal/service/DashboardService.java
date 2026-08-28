/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.awenocorp.abarroteria.kinal.service;
import javafx.collections.ObservableList;
import main.java.com.awenocorp.abarroteria.kinal.model.Producto;
import main.java.com.awenocorp.abarroteria.kinal.repository.ProductoRepository;
/**
 *
 * @author informatica
 */
public class DashboardService {
    private final ProductoRepository productoRepository;
    
    public DashboardService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
    
    public ObservableList<Producto> findProducto(){
        if(productoRepository.findAll() == null) {
            throw new RuntimeException("Sin productos");
        }else{
            return productoRepository.findAll();
        }
    }

    public void eliminarProducto(String idProducto){
        productoRepository.delete(idProducto);
    }
}