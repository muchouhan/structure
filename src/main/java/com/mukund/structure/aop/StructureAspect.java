package com.mukund.structure.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class StructureAspect {

	private Logger logger = Logger.getLogger(this.getClass());

	// What kind of method calls I would intercept
	// execution(* PACKAGE.*.*(..))
	// Weaving & Weaver
//	@Before("execution(* *.*(..))")
	public void before(JoinPoint joinPoint) {
		// Advice
		logger.info(" Invoke Workflow DAO method ");
		logger.warn(" Allowed execution for "+ joinPoint);
	}

//	@AfterReturning(value = "execution(* com.mukund.structure.service.*.findWorkFlowBookById(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
//		logger.info("{} returned with value {}", joinPoint, result);
	}

//	@After(value = "execution(* com.mukund.structure.service.*.findWorkFlowBookById(..))")
	public void after(JoinPoint joinPoint) {
//		logger.info("after execution of {}", joinPoint);
	}

	@Around("execution(* com.mukund.structure.service.*.findWorkFlowBookById(..))")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time Taken by "+joinPoint+" is "+  timeTaken);
	}
}
