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
import java.util.Optional;

@Service
public class AdminService {
    static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired // Inyecta automáticamente una instancia de UserRepository.
    private UserRepository userRepository;

    public void registerAdminUser(UsuarioAdminRegisterRequest userRequest, String currentUser) {
        // Verificar si el usuario actual es un administrador
        User requestingUser = userRepository.findByUsernameAndAccountType(currentUser, User.AccountType.ADMIN)
                .orElseThrow(() -> new RuntimeException("Only ADMIN users can register other admins"));

        // Verificar si ya existe un usuario con el mismo correo electrónico
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        // Crear la entidad User a partir del DTO
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.ADMIN);

        // Encriptar la contraseña con el nivel de seguridad adecuado para administradores
        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);  // El usuario está habilitado inicialmente
        user.setCreatedAt(LocalDateTime.now());

        // Guardar el usuario en el repositorio
        userRepository.save(user);
    }

    public void createDefaultAdminUser() {
        logger.info("CREANDO USUARIO ADMIN DEFAULT");
        // Verificar si ya existe un usuario administrador por defecto
        if (userRepository.existsByUsernameAndAccountType("admin", User.AccountType.ADMIN)) {
            logger.info("Default admin user already exists.");
            return;
        }

        logger.info("Creating default admin user...");

        // Crear el usuario administrador por defecto
        User defaultAdmin = new User();
        defaultAdmin.setEmail("admin@example.com");
        defaultAdmin.setUsername("admin");
        defaultAdmin.setAccountType(User.AccountType.ADMIN);
        defaultAdmin.setEnabled(true);
        defaultAdmin.setCreatedAt(LocalDateTime.now());

        logger.info("USUARIO RELLENO, FALTA LA CONTRASEÑA");

        // Encriptar la contraseña
        String encryptedPassword = encryptPassword("adminPassword");
        defaultAdmin.setPassword(encryptedPassword);

        logger.info("CONTRASEÑA ESTABLECIDAD");

        // Guardar el usuario por defecto en el repositorio
        userRepository.save(defaultAdmin);

        logger.info("Default admin user created successfully.");
    }

    private String encryptPassword(String password) {
        logger.info("Contraseña a encriptar: {}", password);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(20);
        return encoder.encode(password);
    }
}
