package aop.annotation.service.impl;

import org.springframework.stereotype.Component;
import aop.annotation.OperateLog;
import aop.annotation.service.AnnotationService;

@Component
public class AnnotationServiceImpl implements AnnotationService {
	@OperateLog(desc = "增加")
	public void add() {
		System.out.println("调用了" + this.getClass()+"增加了");
	}

	@Override
	public void update() {
		System.out.println("调用了" + this.getClass() + "更新了 ");
	}

}
