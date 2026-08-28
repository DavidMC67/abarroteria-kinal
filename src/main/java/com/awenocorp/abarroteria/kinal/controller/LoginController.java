package main.java.com.awenocorp.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.awenocorp.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.awenocorp.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.awenocorp.abarroteria.kinal.service.AuthService;
import main.java.com.awenocorp.abarroteria.kinal.util.SceneManager;


public class LoginController implements Initializable {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private TextField txtFieldEmail;

    @FXML
    private PasswordField txtFieldPassword;
 
    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleLogin() throws Exception{
        if(txtFieldEmail.getText().isEmpty() || txtFieldPassword.getText().isEmpty()){
            sceneManager.showAlertInfo("hay campos sin llenar", "No puedes dejar espacios en blanco", "Intenta de nuevo", Alert.AlertType.INFORMATION);
        } else {
            try{
                LoginDTOResponse response = authService.login(new LoginDTORequest(txtFieldEmail.getText(), txtFieldPassword.getText()));
                sceneManager.showAlertInfo("Bienvenido", "Es bueno verte:"+ response.getNombre(),"Inicio de sesion correcto", Alert.AlertType.INFORMATION);
                
                // Transición al dashboard usando el SceneManager
                sceneManager.showDashboardView();
                
            } catch(RuntimeException e){
                sceneManager.showAlertInfo("Error al iniciar sesion","Verificar campos", "No se ha podido iniciar sesion", Alert.AlertType.WARNING);
            } catch(java.io.IOException e){
                sceneManager.showAlertInfo("Error de navegación", "Dashboard", "No se pudo cargar la vista del dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}