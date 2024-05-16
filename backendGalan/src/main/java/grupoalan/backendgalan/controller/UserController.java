package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.User;
import grupoalan.backendgalan.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // Define la ruta base para todas las operaciones relacionadas con usuarios.
public class UserController {
    @Autowired
    private UserService userService;

    // Punto de entrada para registrar un nuevo usuario.
    @PostMapping("register")
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
        return ResponseEntity.status(401).build(); // Retorna 401 si las credenciales son incorrectas o la cuenta no está habilitada.
    }

    // Punto de entrada para habilitar un usuario profesional.
    @PostMapping("/enable/{userId}")
    public ResponseEntity<User> enableUser(@PathVariable Long userId) {
        User enabledUser = userService.enableUser(userId);
        return ResponseEntity.ok(enabledUser);
    }
}
