package co.com.bancolombia.usecase.registeruser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;

import java.math.BigDecimal;

public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {

        validateUser(user);

        if (userRepository.existsByEmail(user.getCorreoElectronico())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        return userRepository.save(user);
    }

    private void validateUser(User user) {

        if (user.getNombres() == null || user.getNombres().isBlank()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }

        if (user.getApellidos() == null || user.getApellidos().isBlank()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }

        if (user.getCorreoElectronico() == null || user.getCorreoElectronico().isBlank()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio");
        }

        if (!user.getCorreoElectronico().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }

        if (user.getSalarioBase() == null) {
            throw new IllegalArgumentException("El salario base es obligatorio");
        }

        if (user.getSalarioBase().compareTo(BigDecimal.ZERO) < 0
                || user.getSalarioBase().compareTo(new BigDecimal("15000000")) > 0) {
            throw new IllegalArgumentException("El salario debe estar entre 0 y 15.000.000");
        }
    }
}