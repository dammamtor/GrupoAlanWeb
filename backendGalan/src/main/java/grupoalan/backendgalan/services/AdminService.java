package grupoalan.backendgalan.services;

import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.UsuarioAdminRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioProfesionalRegisterRequest;
import grupoalan.backendgalan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {
    static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired // Inyecta automáticamente una instancia de UserRepository.
    private UserRepository userRepository;

    public void registerAdminUser(UsuarioAdminRegisterRequest userRequest) {
        // Verificar si ya existe un usuario con el mismo correo electrónico
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        // Verificar que el tipo de cuenta sea ADMIN
        if (userRequest.getAccountType() != UsuarioAdminRegisterRequest.AccountType.ADMIN) {
            throw new RuntimeException("Only users with account type ADMIN are allowed to register");
        }

        // Crear la entidad User a partir del DTO
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.ADMIN);

        // Encriptar la contraseña
        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        // Guardar el usuario en el repositorio
        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(30);
        return encoder.encode(password);
    }
}
