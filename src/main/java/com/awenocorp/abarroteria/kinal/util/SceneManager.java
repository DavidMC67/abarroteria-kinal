
package main.java.com.awenocorp.abarroteria.kinal.util;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.awenocorp.abarroteria.kinal.controller.DashboardController;
import main.java.com.awenocorp.abarroteria.kinal.controller.LoginController;
import main.java.com.awenocorp.abarroteria.kinal.repository.AuthRepository;
import main.java.com.awenocorp.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.awenocorp.abarroteria.kinal.service.AuthService;
import main.java.com.awenocorp.abarroteria.kinal.service.DashboardService;


public class SceneManager {
    
    //atributos
    private final Stage stage;
    private final String FXML_PATH = "/main/resources/view/";
    
    //constructor 
    public SceneManager(Stage stage){
        this.stage = stage;
    }   
    
    //metodos
    public void showLoginView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/login-view.fxml"));
        loader.setControllerFactory(
        clazz ->{
          if(clazz == LoginController.class) {
              AuthRepository authRepository = new AuthRepository();
              AuthService authService = new AuthService(authRepository);
              return new LoginController(authService,this);
          } 
          try{
              return clazz.getDeclaredConstructor().newInstance();
          }catch(Exception e){
              throw new RuntimeException("Error al crear el constructor" + e.getMessage());
          }
        }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setResizable(false);
    }
    
    //dashboard Stage
    public void showDashboardView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard-view.fxml"));
        loader.setControllerFactory(
        clazz -> {
           if(clazz == DashboardController.class){
            ProductoRepository productoRepository = new ProductoRepository();
            DashboardService dashboardService = new DashboardService(productoRepository);
            return new DashboardController(dashboardService, this);
        }
        try{
            return clazz.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            throw new RuntimeException("Error al crear el constructor (Dashboard)");
        }
        });
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    //ventana modal reutilizable
    public void showAlertInfo(String head, String title, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setHeaderText(head);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


