package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.model.request.UsuarioRequest;
import grupoalan.backendgalan.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde localhost:4200
@RequestMapping("/grupo-alan/users") // Define la ruta base para todas las operaciones relacionadas con usuarios.
public class UserController {
    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Punto de entrada para registrar un nuevo usuario.
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
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

    // Punto de entrada para habilitar un usuario profesional.
    @PostMapping("/enable/{userId}")
    public ResponseEntity<User> enableUser(@PathVariable Long userId) {
        User enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
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

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UsuarioRequest usuarioRequest) {
        if (userService.authenticateUser(usuarioRequest)) {
            return ResponseEntity.ok("Usuario autenticado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
