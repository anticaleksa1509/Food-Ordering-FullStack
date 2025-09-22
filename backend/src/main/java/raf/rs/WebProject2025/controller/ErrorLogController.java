package raf.rs.WebProject2025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rs.WebProject2025.entities.ErrorLog;
import raf.rs.WebProject2025.service.ErrorLogService;

import java.util.List;

@RestController
@RequestMapping("/api/errors")
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    public ErrorLogController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    @PostMapping
    public ResponseEntity<Void> logError(@RequestParam Long userId, @RequestParam String message) {
        errorLogService.saveError(userId, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ErrorLog>> getUserErrors(@PathVariable Long userId) {
        return ResponseEntity.ok(errorLogService.getErrorsForUser(userId));
    }
}