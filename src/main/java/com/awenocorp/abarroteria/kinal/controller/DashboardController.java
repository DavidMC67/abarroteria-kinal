package main.java.com.awenocorp.abarroteria.kinal.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.awenocorp.abarroteria.kinal.model.Producto;
import main.java.com.awenocorp.abarroteria.kinal.service.DashboardService;
import main.java.com.awenocorp.abarroteria.kinal.util.SceneManager;


public class DashboardController implements Initializable {
    private DashboardService dashboardService;
    private SceneManager sceneManager;
    @FXML
    private TableView<Producto> tableProducto;
    @FXML
    private TableColumn<Producto, String> tableColumnIdProducto;
    @FXML
    private TableColumn<Producto, String> tableColumnNombreProducto;
    @FXML
    private TableColumn<Producto, Integer> tableColumnStock;
    @FXML
    private TableColumn<Producto, BigDecimal> tableColumnPrecio;

    public DashboardController(DashboardService dashboardService, SceneManager sceneManager){
        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleLoadDataTableView();
    }

    @FXML
    private void handleLoadDataTableView(){
        tableColumnIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        tableColumnNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        tableColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableProducto.setItems(dashboardService.findProducto());
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        Producto seleccionado = tableProducto.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Sin selección");
            alerta.setHeaderText(null);
            alerta.setContentText("Selecciona un producto de la tabla para eliminarlo.");
            alerta.showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar \"" + seleccionado.getNombreProducto() + "\"?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta.getButtonData().isDefaultButton()) {
                // AJUSTA este nombre de método al que tenga tu DashboardService
                dashboardService.eliminarProducto(seleccionado.getIdProducto());
                tableProducto.getItems().remove(seleccionado);
            }
        });
    }
}