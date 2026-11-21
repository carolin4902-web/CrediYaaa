package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.RegisterUserRequest;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.registeruser.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/usuarios")
public class RegisterUserHandler {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserHandler(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody RegisterUserRequest request) {

        User user = User.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .fechaNacimiento(request.getFechaNacimiento())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .correoElectronico(request.getCorreoElectronico())
                .salarioBase(request.getSalarioBase())
                .build();

        registerUserUseCase.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}