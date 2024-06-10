package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.SessionInfo;
import grupoalan.backendgalan.model.request.UsuarioParticularRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioProfesionalRegisterRequest;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde localhost:4200
@RequestMapping("/grupo-alan/users") // Define la ruta base para todas las operaciones relacionadas con usuarios.
public class UserController {
    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UsuarioParticularRegisterRequest userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registro exitoso. Por favor, revisa tu correo electrónico para confirmar tu cuenta.\"}");
    }

    @PostMapping("/registerProfessionalUser")
    public ResponseEntity<String> registerProfessionalUser(
            @RequestBody UsuarioProfesionalRegisterRequest userRequest
    ) {
        try {
            userService.registerProfessionalUser(userRequest);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registro exitoso\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        boolean isVerified = userService.verifyUser(token);
        if (isVerified) {
            return ResponseEntity.ok("{\"message\": \"Verificación exitosa. Ahora puedes iniciar sesión.\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de verificación inválido o expirado.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            String token = userService.authenticateUser(usuarioRequest);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/verify-credentials/{userId}")
    public ResponseEntity<Boolean> verifyCredentials(
            @PathVariable Long userId, @RequestBody String password) {
        logger.info("Verifying credentials for user with ID: {}", userId);
        boolean credentialsValid = userService.verificarCredenciales(userId, password);
        logger.info("Credentials verification result for user with ID {}: {}", userId, credentialsValid);
        return ResponseEntity.ok(credentialsValid);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> initiatePasswordReset(@RequestParam String email) {
        userService.initiatePasswordReset(email);
        return ResponseEntity.ok("Se ha enviado un correo electrónico con instrucciones para restablecer tu contraseña.");
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody String newPassword) {
        boolean isResetSuccessful = userService.resetPassword(token, newPassword);
        if (isResetSuccessful) {
            return ResponseEntity.ok("Contraseña restablecida exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de restablecimiento de contraseña inválido o expirado.");
        }
    }
    @PostMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Usuario eliminado exitosamente.");
    }

    @GetMapping("/find-by-username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        userService.updatePassword(userId, newPassword);
        return ResponseEntity.ok("Contraseña actualizada exitosamente.");
    }

    @PostMapping("/update-user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }
}