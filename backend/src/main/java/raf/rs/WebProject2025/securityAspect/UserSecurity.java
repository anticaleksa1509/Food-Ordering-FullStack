package raf.rs.WebProject2025.securityAspect;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.rs.WebProject2025.service.TokenService;

@Aspect
@Configuration
public class UserSecurity {

    @Autowired
    private TokenService tokenService;

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanCreateOrder)")
    public Object canCreateOrder(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCanCreate = claims.get("can_create_order", Boolean.class);
        if(isCanCreate != null && isCanCreate)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanScheduleOrder)")
    public Object schedule(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCan = claims.get("can_schedule_order", Boolean.class);
        if(isCan != null && isCan)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }
    @Around("@annotation(raf.rs.WebProject2025.annotations.CanCancelOrder)")
    public Object canCancelOrder(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCan = claims.get("can_cancel_order", Boolean.class);
        if(isCan != null && isCan)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanTrackOrder)")
    public Object canTrackOrder(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCan = claims.get("can_track_order", Boolean.class);
        if(isCan != null && isCan)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanSearchOrder)")
    public Object canSearchOrder(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean searchOrder = claims.get("can_search_order", Boolean.class);
        if(searchOrder != null && searchOrder)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have required permissions " +
                "to perform this action",HttpStatus.FORBIDDEN);

    }



    @Around("@annotation(raf.rs.WebProject2025.annotations.CanCreateUser)")
    public Object canCreateUser(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCanCreate = claims.get("can_create", Boolean.class);
        if(isCanCreate != null && isCanCreate)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanReadUser)")
    public Object canReadUser(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                    break;
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCanRead = claims.get("can_read", Boolean.class);
        if(isCanRead != null && isCanRead)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have required permissions " +
                "to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanDeleteUser)")
    public Object canDeleteUser(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCanDelete = claims.get("can_delete", Boolean.class);
        if(isCanDelete != null && isCanDelete)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

    @Around("@annotation(raf.rs.WebProject2025.annotations.CanUpdateUser)")
    public Object canUpdateUser(ProceedingJoinPoint joinPoint) throws Throwable{
        String token = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        for(int i = 0; i < methodSignature.getParameterNames().length; i++){
            if(methodSignature.getParameterNames()[i].equals("authorization")){
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer")){
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }
        if(token == null)
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        Claims claims = tokenService.parseToken(token);
        Boolean isCanUpdate = claims.get("can_update", Boolean.class);
        if(isCanUpdate != null && isCanUpdate)
            return joinPoint.proceed();
        return new ResponseEntity<>("You do not have the required permissions" +
                " to perform this action",HttpStatus.FORBIDDEN);

    }

}
