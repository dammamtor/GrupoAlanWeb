package grupoalan.backendgalan.services;

import grupoalan.backendgalan.exceptions.UserAlreadyEnabledException;
import grupoalan.backendgalan.exceptions.UserNotFoundException;
import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.UsuarioAdminRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public void registerAdminUser(UsuarioAdminRegisterRequest userRequest, String currentUser) {
        User requestingUser = userRepository.findByUsernameAndAccountType(currentUser, User.AccountType.ADMIN)
                .orElseThrow(() -> new RuntimeException("Only ADMIN users can register other admins"));

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setAccountType(User.AccountType.ADMIN);

        String encryptedPassword = encryptPassword(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public void createDefaultAdminUser() {
        logger.info("CREANDO USUARIO ADMIN DEFAULT");
        if (userRepository.existsByUsernameAndAccountType("admin", User.AccountType.ADMIN)) {
            logger.info("Default admin user already exists.");
            return;
        }

        User defaultAdmin = new User();
        defaultAdmin.setEmail("admin@example.com");
        defaultAdmin.setUsername("admin");
        defaultAdmin.setAccountType(User.AccountType.ADMIN);
        defaultAdmin.setEnabled(true);
        defaultAdmin.setCreatedAt(LocalDateTime.now());

        String encryptedPassword = encryptPassword("adminPassword");
        defaultAdmin.setPassword(encryptedPassword);

        userRepository.save(defaultAdmin);

        logger.info("Default admin user created successfully.");
    }

    private String encryptPassword(String password) {
        logger.info("Contraseña a encriptar: {}", password);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(20);
        return encoder.encode(password);
    }

    public void manageUser(Long userId, boolean enable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado según ID: " + userId));

        if (enable) {
            if (user.isEnabled()) {
                throw new UserAlreadyEnabledException("El usuario con ID " + userId + "ya está habilitado");
            }
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            userRepository.delete(user);
        }
    }

    public String authenticateAdmin(UsuarioRequest usuarioRequest) {
        User admin = userRepository.findByUsernameAndAccountType(usuarioRequest.getNombreUsuario(), User.AccountType.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(20);
        if (encoder.matches(usuarioRequest.getContrasena(), admin.getPassword())) {
            return generateToken(admin);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getCurrentUserFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace("Bearer ", "")).getBody();
        return claims.getSubject();
    }
}
