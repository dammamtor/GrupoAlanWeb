package grupoalan.backendgalan.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.controller.UserController;
import grupoalan.backendgalan.exceptions.UserNotFoundException;
import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.VerificationToken;
import grupoalan.backendgalan.model.request.UsuarioParticularRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioProfesionalRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.repository.UserRepository;
import grupoalan.backendgalan.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired // Inyecta automáticamente una instancia de UserRepository.
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    // Método para registrar un nuevo usuario.
    public void registerUser(UsuarioParticularRegisterRequest userRequest) {
        // Verificar si ya existe un usuario con el mismo correo electrónico
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        // Verificar que el tipo de cuenta sea PARTICULAR
        if (userRequest.getAccountType() != UsuarioParticularRegisterRequest.AccountType.PARTICULAR) {
            throw new RuntimeException("Only users with account type PARTICULAR are allowed to register");
        }

        // Verificar que las contraseñas coincidan
        if (!userRequest.getPassword().equals(userRequest.getRepeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Crear la entidad User a partir del DTO
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setCountry(userRequest.getCountry());
        user.setCity(userRequest.getCity());
        user.setAddress(userRequest.getAddress());
        user.setPostalCode(userRequest.getPostalCode());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.PARTICULAR);

        // Encriptar la contraseña
        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());

        // Guardar el usuario en el repositorio
        User savedUser = userRepository.save(user);

        // Crear y guardar el token de verificación
        String verificationToken = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken();
        token.setToken(verificationToken);
        token.setUserId(savedUser.getId());
        token.setExpiryDate(LocalDateTime.now().plusHours(24)); // El token expira en 24 horas
        verificationTokenRepository.save(token);

        // Construir el enlace de verificación
        String verificationLink = "http://localhost:4200/verify/" + verificationToken;

        // Enviar el correo de verificación
        String subject = "Confirmación de registro";
        String body = String.format("Hola %s,\n\nGracias por registrarte. Por favor, haz clic en el siguiente enlace para confirmar tu correo electrónico:\n\n%s\n\nSaludos,\nTuApp",
                savedUser.getUsername(), verificationLink);
        emailService.sendVerificationEmail(savedUser.getEmail(), subject, body);
    }

    public void registerProfessionalUser(UsuarioProfesionalRegisterRequest userRequest) {
        // Verificar si ya existe un usuario con el mismo correo electrónico
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        // Verificar que el tipo de cuenta sea PROFESIONAL
        if (userRequest.getAccountType() != UsuarioProfesionalRegisterRequest.AccountType.PROFESSIONAL) {
            throw new RuntimeException("Only users with account type PROFESIONAL are allowed to register");
        }

        // Crear la entidad User a partir del DTO
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.PROFESSIONAL);
        user.setCompanyName(userRequest.getCompanyName());
        user.setCompanyAddress(userRequest.getCompanyAddress());
        user.setCompanyPhoneNumber(userRequest.getCompanyPhoneNumber());

        // Encriptar la contraseña
        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(false);  // El usuario no está habilitado inicialmente
        user.setCreatedAt(LocalDateTime.now());

        // Guardar el usuario en el repositorio
        userRepository.save(user);
    }

    public boolean verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = userRepository.findById(verificationToken.getUserId()).orElse(null);
        if (user == null) {
            return false;
        }

        user.setEnabled(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        String subject = "Verificación exitosa";
        String body = String.format("Hola %s,\n\nTu correo electrónico ha sido verificado exitosamente. Ahora puedes iniciar sesión en tu cuenta.\n\nSaludos,\nTuApp",
                user.getUsername());
        emailService.sendVerificationEmail(user.getEmail(), subject, body);

        return true;
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
            user.setName(updatedUser.getName());
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
        String contrasena = usuarioRequest.getContrasena();
        logger.info("Nombre de usuario: {}", nombreUsuario);
        logger.info("Password de usuario: {}", contrasena);

        Optional<User> usuarioOptional = userRepository.findByUsername(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            User usuario = usuarioOptional.get();
            if (!usuario.isEnabled()) {
                logger.info("Usuario deshabilitado: {}", nombreUsuario);
                return false; // Usuario deshabilitado
            }

            BCryptPasswordEncoder encoder;
            switch (usuario.getAccountType()) {
                case ADMIN:
                    encoder = new BCryptPasswordEncoder(20);
                    break;
                case PARTICULAR, PROFESSIONAL:
                    encoder = new BCryptPasswordEncoder(15);
                    break;
                default:
                    logger.info("Tipo de cuenta desconocido para el usuario: {}", nombreUsuario);
                    return false;
            }

            return encoder.matches(contrasena, usuario.getPassword());
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
        // Buscar el usuario por su ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado según ID: " + userId));

        // Habilitar el usuario
        user.setEnabled(true);

        // Guardar y devolver el usuario actualizado
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + userId));
        String encryptedPassword = encryptPassword(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String resetToken = generateResetToken();

            // Guardar el token de reseteo en la base de datos utilizando la entidad VerificationToken
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(resetToken);
            verificationToken.setUserId(user.getId());
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token expira en 24 horas
            verificationTokenRepository.save(verificationToken);

            // Enviar correo electrónico con el token de reseteo y las instrucciones
            String resetLink = "http://localhost:8081/reset-password?token=" + resetToken;
            String subject = "Instrucciones para restablecer la contraseña";
            String body = "Hola " + user.getUsername() + ",\n\nParece que has solicitado restablecer tu contraseña. " +
                    "Haz clic en el siguiente enlace para completar el proceso:\n\n" + resetLink +
                    "\n\nSi no solicitaste este restablecimiento, puedes ignorar este mensaje.\n\nSaludos,\nTuApp";
            emailService.sendVerificationEmail(email, subject, body);
        }
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    public boolean resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        User user = userRepository.findById(verificationToken.getUserId()).orElse(null);
        if (user == null) {
            // No se pudo encontrar el usuario asociado con el token
            return false;
        }

        // Actualizar la contraseña del usuario
        user.setPassword(encryptPassword(newPassword));
        userRepository.save(user);

        // Eliminar el token de la base de datos, ya que ya no es necesario
        verificationTokenRepository.delete(verificationToken);

        // Enviar correo electrónico de confirmación de restablecimiento de contraseña
        String subject = "Contraseña restablecida exitosamente";
        String body = "Hola " + user.getUsername() + ",\n\nTu contraseña ha sido restablecida exitosamente.\n\n" +
                "Si no realizaste esta acción, por favor contacta a nuestro equipo de soporte.\n\nSaludos,\nTuApp";
        emailService.sendVerificationEmail(user.getEmail(), subject, body);

        return true;
    }


}
