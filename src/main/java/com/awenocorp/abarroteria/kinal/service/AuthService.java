package main.java.com.awenocorp.abarroteria.kinal.service;

import main.java.com.awenocorp.abarroteria.kinal.dto.request.LoginDTORequest;
import main.java.com.awenocorp.abarroteria.kinal.dto.response.LoginDTOResponse;
import main.java.com.awenocorp.abarroteria.kinal.repository.AuthRepository;
import main.java.com.awenocorp.abarroteria.kinal.security.jbcrypt.BCrypt;

public class AuthService {
    private final AuthRepository authRepository;
 
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    public LoginDTOResponse login(LoginDTORequest request){
        if(request == null){
            throw new RuntimeException("Los datos están vacios");
        }else if(request.getEmail() == null || request.getPassword() == null){
            throw new RuntimeException("Uno o los dos campos están vacios");            
        }else if(request.getEmail().isEmpty() || request.getPassword().isEmpty()){
            throw new RuntimeException("No puedes dejar compos en blanco");
        }
        LoginDTOResponse response = authRepository.findUserByEmail(request);
        if(response == null || response.getContrasenaHash() == null){
            throw new RuntimeException("No se ha podido concretar la operación.");
        }else{
            if(BCrypt.checkpw(request.getPassword(), response.getContrasenaHash())){
                return response; 
//LoginDTOResponse(response.getNombre(), response.getApellido(), response.getNombreRol());
            }
        }
        return null;
    }
}