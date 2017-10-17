package aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aop.annotation.dao.AnnotationDao;
import aop.annotation.service.AnnotationService;

@Component
@Aspect
public class AspectAdvice {
	
//	@Autowired
//	private AnnotationDao annotationDao;
	
	@Pointcut("execution(* aop.annotation.*.*(..))")
	public void anyMethod(){
		
	}
	
//	@Pointcut("execution(* aop.annotation.*.*(..)) && @annotation(operateLog)")
//	public void annotation(){
//	
//	}
	//"execution(* aop.annotation.*.*(..)) && @annotation(operateLog)"
	@Before("execution(* aop.annotation.service..*.*(..)) && @annotation(operate)")
	public void serviceDoBefor(JoinPoint jp ,OperateLog operate){
		System.out.println("=========进去serviceDoBefor");
		System.out.println(operate.desc());
		//annotationDao.insert();
	}
	
	/**
     * 前置通知
     * 
     * @param jp
     */
    @Before(value = "execution(* aop.annotation.*.*(..))")
    public void doBefore(JoinPoint jp) {
        System.out.println("===========进入before advice============ \n");
        System.out.print("准备在" + jp.getTarget().getClass() + "对象上用");
        System.out.print(jp.getSignature().getName() + "方法进行对 '");
        System.out.print(jp.getArgs()[0] + "'进行删除！\n\n");
        System.out.println("要进入切入点方法了 \n");
    }
    /**
     * 后置通知
     * 
     * @param jp
     *            连接点
     * @param result
     *            返回值
     */
    @AfterReturning(value = "execution(* aop.annotation.*.*(..))", returning = "result")
    public void doAfter(JoinPoint jp, String result) {
    	System.out.println("==========进入after advice=========== \n");
    	//annotationDao.insert();
        System.out.println("切入点方法执行完了 \n");
        System.out.print(jp.getArgs()[0] + "在");
        System.out.print(jp.getTarget().getClass() + "对象上被");
        System.out.print(jp.getSignature().getName() + "方法删除了");
        System.out.print("只留下：" + result + "\n\n");
    }
    /**
    * 异常通知
    * 
    * @param jp
    * @param e
    */
   @AfterThrowing(value = "execution(* aop.annotation.*.*(..))", throwing = "e")
   public void doThrow(JoinPoint jp, Throwable e) {
       System.out.println("删除出错啦");
       System.out.println("例外通知");
       System.out.println(e.getMessage());
   }

}
