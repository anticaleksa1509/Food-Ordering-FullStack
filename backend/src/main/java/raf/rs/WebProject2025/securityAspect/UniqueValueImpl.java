package raf.rs.WebProject2025.securityAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.UserRepo;

import java.util.List;

@Aspect
@Configuration
public class UniqueValueImpl {

    @Autowired
    UserRepo userRepo;

    @Around("@annotation(raf.rs.WebProject2025.annotations.UniqueValue)")
    public Object uniqueValueMethod(ProceedingJoinPoint joinPoint) throws Throwable{

        Object obj[] = joinPoint.getArgs();
        User user = null;
        for(Object o : obj){
            if(o instanceof User){
                 user = (User) o;
                break;
            }
        }

        if(userRepo.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("User with that email address already exist!",HttpStatus.CONFLICT);
        }

        return joinPoint.proceed();
    }
}
