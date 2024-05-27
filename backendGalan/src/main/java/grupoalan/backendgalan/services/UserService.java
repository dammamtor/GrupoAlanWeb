package grupoalan.backendgalan.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.controller.UserController;
import grupoalan.backendgalan.exceptions.UserNotFoundException;
import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired // Inyecta automáticamente una instancia de UserRepository.
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;

    // Método para registrar un nuevo usuario.
    public User registerUser(User user) {
        // Verificar si ya existe un usuario con el mismo correo electrónico
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        // Encriptar la contraseña
        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        // Si la cuenta es profesional, no habilitarla inmediatamente.
        user.setEnabled(user.getAccountType() != User.AccountType.PROFESSIONAL);

        // Establecer la fecha de creación
        user.setCreatedAt(LocalDateTime.now());

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Enviar correo de confirmación de registro
        String subject = "Confirmación de registro";
        String body = String.format("Hola %s,\n\nGracias por registrarte. Aquí están tus datos de registro:\n\nNombre: %s\nCorreo: %s\n\nSaludos,\nTuApp",
                user.getUsername(), user.getFirstName(), user.getEmail());
        emailService.sendVerificationEmail(user.getEmail(), subject, body);

        return savedUser;
    }

    public User updateUser(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Update common fields
            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            user.setAccountType(updatedUser.getAccountType());
            user.setEnabled(updatedUser.isEnabled());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setCreatedAt(updatedUser.getCreatedAt());

            // Update fields specific to business account
            if (user.getAccountType() == User.AccountType.PROFESSIONAL) {
                user.setCompanyName(updatedUser.getCompanyName());
                user.setCompanyAddress(updatedUser.getCompanyAddress());
                user.setCompanyPhoneNumber(updatedUser.getCompanyPhoneNumber());
            }

            return userRepository.save(user);
        } else {
            // Handle the case where the user with the given ID is not found
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

    }

    public boolean authenticateUser(UsuarioRequest usuarioRequest) {
        String nombreUsuario = usuarioRequest.getNombreUsuario();
        String contraseña = usuarioRequest.getContraseña();
        logger.info("Nombre de usuario: {}", nombreUsuario);

        Optional<User> usuarioOptional = userRepository.findByUsername(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            User usuario = usuarioOptional.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(contraseña, usuario.getPassword());
        } else {
            return false; // Usuario no encontrado
        }
    }

    // Método para verificar las credenciales del usuario
    public boolean verificarCredenciales(Long userId, String contraseñaJSON) {
        Optional<User> usuarioOpcional = userRepository.findById(userId);
        if (usuarioOpcional.isPresent()) {
            User usuario = usuarioOpcional.get();
            logger.info("Usuario encontrado: {}", usuario);
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder(15);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(contraseñaJSON);
                String contraseña = jsonNode.get("password").asText();
                logger.info("Contraseña introducida: {}", contraseña);

                String contraseñaCodificada = codificador.encode(contraseña);
                logger.info("Contraseña codificada introducida: {}", contraseñaCodificada);
                logger.info("Contraseña almacenada: {}", usuario.getPassword());

                boolean credencialesValidas = codificador.matches(contraseña, usuario.getPassword());
                if (credencialesValidas) {
                    logger.info("Credenciales válidas para usuario con ID {}", userId);
                } else {
                    logger.info("Credenciales inválidas para usuario con ID {}", userId);
                }
                return credencialesValidas;
            } catch (Exception e) {
                logger.error("Error al obtener la contraseña introducida: {}", e.getMessage());
                return false; // Si ocurre un error, retornamos false
            }
        } else {
            logger.error("Usuario con ID {} no encontrado", userId);
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
        }
    }

    // Método para encontrar un usuario por su correo electrónico.
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado segun email: " + email);
        }
        return user;
    }

    // Método para habilitar un usuario por su ID.
    public User enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado segun ID: " + userId));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
        return encoder.encode(password);
    }

    // Método para obtener la lista de todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Restablecimiento de Contraseña


    //VERIFICACION DE CORREO ELECTRONICO

}
