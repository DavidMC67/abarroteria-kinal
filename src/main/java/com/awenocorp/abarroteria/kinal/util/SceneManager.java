
package main.java.com.awenocorp.abarroteria.kinal.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.com.awenocorp.abarroteria.kinal.controller.LoginController;
import main.java.com.awenocorp.abarroteria.kinal.repository.AuthRepository;
import main.java.com.awenocorp.abarroteria.kinal.service.AuthService;


public class SceneManager {
    
    //atributos
    private final Stage stage;
    
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/dashboard-view.fxml"));
        
        // Si tu DashboardController también necesita inyección de dependencias o servicios en el futuro, 
        // puedes configurarlo aquí con un controllerFactory similar al del login.
        
        Parent root = loader.load();
        Scene scene = new Scene(root); // O puedes definirle un ancho y alto específico si lo prefieres
        stage.setScene(scene);
        stage.setTitle("Dashboard - Abarrotería Kinal");
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
