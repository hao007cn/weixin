package aop.annotation.dao.impl;
import org.springframework.stereotype.Repository;
import aop.annotation.dao.AnnotationDao;

public class AnnotationDaoImpl implements AnnotationDao {
	@Override
	public void insert() {
			System.out.println(this.getClass());
	}
	
}
