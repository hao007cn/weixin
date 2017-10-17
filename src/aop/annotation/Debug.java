package aop.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aop.annotation.service.AnnotationService;

public class Debug {
	 public static void main(String[] args) {
	        //ApplicationContext context = new ClassPathXmlApplicationContext("aop/annotation/annotation_aop.xml");
	        //Business business = (Business) context.getBean("business");
	        //business.delete("猫");
	       // business.add("miao");
		 ApplicationContext context = new ClassPathXmlApplicationContext("aop/annotation/annotation_aop.xml");
		 AnnotationService service = (AnnotationService)context.getBean("annotationServiceImpl");
		 service.add();
		 service.update();
	 }
}
