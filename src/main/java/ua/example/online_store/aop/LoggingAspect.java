package ua.example.online_store.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Pointcut("within(ua.example.online_store.web.controller..*)")
  public void applicationPackagePointcutController() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Around("applicationPackagePointcutController()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isInfoEnabled()) {
      log.info("Enter: {}.{}() with argument[s] = {}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isInfoEnabled()) {
        log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), result);
      }
      return result;
    } catch (IllegalArgumentException e) {
      log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }

  }

  @AfterThrowing(pointcut = "applicationPackagePointcutController()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() with cause = {}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
  }

}