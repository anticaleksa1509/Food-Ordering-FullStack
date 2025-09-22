package raf.rs.WebProject2025.service;

import org.springframework.stereotype.Service;
import raf.rs.WebProject2025.entities.ErrorLog;
import raf.rs.WebProject2025.repositories.ErrorLogRepo;

import java.util.List;

@Service
public class ErrorLogService {

    private final ErrorLogRepo errorLogRepository;

    public ErrorLogService(ErrorLogRepo errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }
    public void saveError(Long userId, String message) {
        ErrorLog errorLog = new ErrorLog(userId, message);
        errorLogRepository.save(errorLog);
    }

    public List<ErrorLog> getErrorsForUser(Long userId) {
        return errorLogRepository.findByUserId(userId);
    }

}
