package raf.rs.WebProject2025.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import raf.rs.WebProject2025.entities.ErrorMessage;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.ErrorRepo;
import raf.rs.WebProject2025.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorService {

    @Autowired
    private ErrorRepo errorRepo;
    @Autowired
    private UserRepo userRepo;
    public void saveErrorMessage(String message,Integer id_order,Integer id_user){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(message);
        errorMessage.setOrder_id(Long.valueOf(id_order));
        errorMessage.setOperation("Order creation");
        errorMessage.setLocalDateTime(LocalDateTime.now());
        errorMessage.setIdUser(Long.valueOf(id_user));
        errorRepo.save(errorMessage);
    }

    public List<ErrorMessage> getMessages(Integer idUser){
        User user = userRepo.findById(Long.valueOf(idUser)).orElseThrow(()->
                new EntityNotFoundException("User not found"));
        if(user.getRole().equals("Administrator")){
            return errorRepo.findAll();
        }
        return errorRepo.findByIdUser(idUser);
    }



 }
