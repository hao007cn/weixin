package aop.annotation;

import org.springframework.stereotype.Component;


public class Business {
	/**
	 * @return
	 */
	public String delete(String obj){
		System.out.println("==========调用切入点：" + obj + "说：你敢删除我！===========\n");
		//throw new RuntimeException("异常了。。");
		return obj + "：瞄～";
	}
	
	 public String add(String obj) {
	        System.out.println("================这个方法不能被切。。。============== \n");
	        return obj + "：瞄～ 嘿嘿！";
	    }
	 
	 public String modify(String obj) {
	        System.out.println("=================这个也设置加入切吧====================\n");
	        return obj + "：瞄改瞄啊！";
	    }

}
