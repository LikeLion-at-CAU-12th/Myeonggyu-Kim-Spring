package likelion12th.session.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ResponseEntity<Health> healthCheck() {
        return ResponseEntity.ok().body(new Health("success", LocalDateTime.now()));
    }

    @Setter
    @Getter
    static class Health {
        String message;
        LocalDateTime time;

        public Health(String message, LocalDateTime time) {
            this.message = message;
            this.time = time;
        }

    }
}
