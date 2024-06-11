package grupoalan.backendgalan.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupoalan.backendgalan.controller.UserController;
import grupoalan.backendgalan.exceptions.UserAlreadyEnabledException;
import grupoalan.backendgalan.exceptions.UserNotFoundException;
import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.VerificationToken;
import grupoalan.backendgalan.model.request.UsuarioParticularRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioProfesionalRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.repository.UserRepository;
import grupoalan.backendgalan.repository.VerificationTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Value("${jwt.secret}")
    private String secret;

    public void registerUser(UsuarioParticularRegisterRequest userRequest) {
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        if (userRequest.getAccountType() != UsuarioParticularRegisterRequest.AccountType.PARTICULAR) {
            throw new RuntimeException("Only users with account type PARTICULAR are allowed to register");
        }

        if (!userRequest.getPassword().equals(userRequest.getRepeatPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

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

        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        String verificationToken = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken();
        token.setToken(verificationToken);
        token.setUserId(savedUser.getId());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(token);

        String verificationLink = "http://localhost:4200/verify/" + verificationToken;

        String subject = "Confirmación de registro";
        String body = String.format("Hola %s,\n\nGracias por registrarte. Por favor, haz clic en el siguiente enlace para confirmar tu correo electrónico:\n\n%s\n\nSaludos,\nTuApp",
                savedUser.getUsername(), verificationLink);
        emailService.sendVerificationEmail(savedUser.getEmail(), subject, body);
    }

    public void registerProfessionalUser(UsuarioProfesionalRegisterRequest userRequest) {
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        if (userRequest.getAccountType() != UsuarioProfesionalRegisterRequest.AccountType.PROFESSIONAL) {
            throw new RuntimeException("Only users with account type PROFESIONAL are allowed to register");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.PROFESSIONAL);
        user.setCompanyName(userRequest.getCompanyName());
        user.setCompanyAddress(userRequest.getCompanyAddress());
        user.setCompanyPhoneNumber(userRequest.getCompanyPhoneNumber());
        user.setCity(userRequest.getCity());
        user.setPostalCode(userRequest.getPostalCode());

        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());

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

            user.setEmail(updatedUser.getEmail());
            user.setUsername(updatedUser.getUsername());
            user.setAccountType(updatedUser.getAccountType());
            user.setEnabled(updatedUser.isEnabled());
            user.setName(updatedUser.getName());
            user.setCreatedAt(updatedUser.getCreatedAt());

            if (user.getAccountType() == User.AccountType.PROFESSIONAL) {
                user.setCompanyName(updatedUser.getCompanyName());
                user.setCompanyAddress(updatedUser.getCompanyAddress());
                user.setCompanyPhoneNumber(updatedUser.getCompanyPhoneNumber());
            }

            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }

    public String authenticateUser(UsuarioRequest usuarioRequest) {
        String nombreUsuario = usuarioRequest.getNombreUsuario();
        String contrasena = usuarioRequest.getContrasena();
        logger.info("Nombre de usuario: {}", nombreUsuario);
        logger.info("Password de usuario: {}", contrasena);

        Optional<User> usuarioOptional = userRepository.findByUsername(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            User usuario = usuarioOptional.get();
            if (!usuario.isEnabled()) {
                logger.info("Usuario deshabilitado: {}", nombreUsuario);
                return null;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
            if (encoder.matches(contrasena, usuario.getPassword())) {
                return generateToken(usuario);
            } else {
                logger.info("Contraseña incorrecta para el usuario: {}", nombreUsuario);
                return null;
            }
        } else {
            logger.info("Usuario no encontrado: {}", nombreUsuario);
            return null;
        }
    }

    public boolean verificarCredenciales(Long userId, String passwordJSON) {
        Optional<User> usuarioOpcional = userRepository.findById(userId);
        if (usuarioOpcional.isPresent()) {
            User usuario = usuarioOpcional.get();
            logger.info("Usuario encontrado: {}", usuario);
            BCryptPasswordEncoder codificador = new BCryptPasswordEncoder(15);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(passwordJSON);
                String password = jsonNode.get("password").asText();
                logger.info("Contraseña introducida: {}", password);

                String EncryptedPassword = codificador.encode(password);
                logger.info("Contraseña codificada introducida: {}", EncryptedPassword);
                logger.info("Contraseña almacenada: {}", usuario.getPassword());

                boolean credencialesValidas = codificador.matches(password, usuario.getPassword());
                if (credencialesValidas) {
                    logger.info("Credenciales válidas para usuario con ID {}", userId);
                } else {
                    logger.info("Credenciales inválidas para usuario con ID {}", userId);
                }
                return credencialesValidas;
            } catch (Exception e) {
                logger.error("Error al obtener la contraseña introducida: {}", e.getMessage());
                return false;
            }
        } else {
            logger.error("Usuario con ID {} no encontrado", userId);
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
        }
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado segun email: " + email);
        }
        return user;
    }

    public User manageUser(Long userId, boolean enable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado según ID: " + userId));

        if (enable) {
            if (user.isEnabled()) {
                throw new RuntimeException("El usuario con ID " + userId + " ya está habilitado");
            }
            user.setEnabled(true);
            return userRepository.save(user);
        } else {
            userRepository.delete(user);
            return null;
        }
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
        return encoder.encode(password);
    }

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

            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(resetToken);
            verificationToken.setUserId(user.getId());
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            verificationTokenRepository.save(verificationToken);

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
            return false;
        }

        user.setPassword(encryptPassword(newPassword));
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        String subject = "Contraseña restablecida exitosamente";
        String body = "Hola " + user.getUsername() + ",\n\nTu contraseña ha sido restablecida exitosamente.\n\n" +
                "Si no realizaste esta acción, por favor contacta a nuestro equipo de soporte.\n\nSaludos,\nTuApp";
        emailService.sendVerificationEmail(user.getEmail(), subject, body);

        return true;
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
}