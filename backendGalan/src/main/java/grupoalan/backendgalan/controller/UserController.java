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

    // Punto de entrada para registrar un nuevo usuario.
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

    // Punto de entrada para iniciar sesión.
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        // Verifica las credenciales y si la cuenta está habilitada.
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword()) && existingUser.isEnabled()) {
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Retorna 401 si las credenciales son incorrectas o la cuenta no está habilitada.
    }

//    // Punto de entrada para habilitar un usuario profesional.
//    @PostMapping("/enable/{userId}")
//    public ResponseEntity<User> enableUser(@PathVariable Long userId) {
//        User enabledUser = userService.enableUser(userId);
//        return ResponseEntity.ok(enabledUser);
//    }

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

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UsuarioRequest usuarioRequest,
                                                   HttpSession session) {
        User authenticatedUser = userService.authenticateUser(usuarioRequest);
        if (authenticatedUser != null) {
            // Usuario autenticado correctamente, establece la sesión con nombre de usuario y tipo de cuenta
            session.setAttribute("currentUser", authenticatedUser.getUsername());
            session.setAttribute("currentAccountType", authenticatedUser.getAccountType().toString());

            logger.info("Usuario actual: {}", authenticatedUser.getUsername());
            logger.info("Tipo de cuenta actual: {}", authenticatedUser.getAccountType().toString());
            logger.info("Usuario {} autenticado correctamente", authenticatedUser.getUsername());
            return ResponseEntity.ok("{\"message\": \"Verificación exitosa. Ahora puedes iniciar sesión.\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @GetMapping("/session-info")
    public ResponseEntity<SessionInfo> getSessionInfo(HttpSession session) {
        logger.info("ESTAS EN SESSION. HORA DE EXTRAER");
        String currentUser = (String) session.getAttribute("currentUser");
        String currentAccountType = (String) session.getAttribute("currentAccountType");
        logger.info("Usuario actual: {}", currentUser);
        logger.info("Tipo de cuenta actual: {}", currentAccountType);

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setCurrentUser(currentUser);
        sessionInfo.setCurrentAccountType(currentAccountType);
        return ResponseEntity.ok(sessionInfo);
    }


    // Punto de entrada para iniciar el proceso de restablecimiento de contraseña.
    @PostMapping("/reset-password")
    public ResponseEntity<String> initiatePasswordReset(@RequestParam String email) {
        userService.initiatePasswordReset(email);
        return ResponseEntity.ok("Se ha enviado un correo electrónico con instrucciones para restablecer tu contraseña.");
    }

    // Punto de entrada para restablecer la contraseña utilizando el token recibido.
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
