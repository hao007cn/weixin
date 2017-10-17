package aop.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//表示该注解用于什么地方
@Target(ElementType.METHOD)
//表示在什么级别保存该注解信息
@Retention(RetentionPolicy.RUNTIME)
//生成文档时记录
@Documented
@Inherited
public @interface OperateLog {
	public String desc();
}
