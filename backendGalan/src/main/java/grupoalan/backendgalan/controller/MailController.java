package grupoalan.backendgalan.controller;

import grupoalan.backendgalan.model.request.MailRequest;
import grupoalan.backendgalan.services.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupo-alan/mails") // Define la ruta base para todas las operaciones relacionadas con usuarios.
public class MailController {
    static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        try {
            emailService.sendVerificationEmail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getBody());
            return ResponseEntity.ok("Correo enviado exitosamente");
        } catch (Exception e) {
            logger.error("Error al enviar el correo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo");
        }
    }
}
