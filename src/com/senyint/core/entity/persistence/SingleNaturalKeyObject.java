package com.senyint.core.entity.persistence;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * provide a single natural key base entity class. it has a generic type of
 * poid. the client have to assign the poid manually in order to maintain the
 * primary key self.
 * <p>
 * 實作上建議；因為poid欄位已經代表了natural
 * key，以「各組檢驗單(SubdivisionForm)」為例，poid表示「各組檢驗單號(FormCode)」這個primary key。
 * </p>
 * <p>
 * 因此繼承這個類別之後，通常必需要做下面兩件事情：<br/>
 * 1. 在類別前宣告override poid的JPA mapping屬性：<br/>
 * 如：<br/>
 * <pre>
 * &#64;Entity
 * &#64;Table(name = "SubdivForm")
 * &#64;AttributeOverride(name = "poid", column = &#64;Column(name = "FormCode", length = 8, nullable = false))
 * public class SubdivisionForm extends PersistentObject&lt;String&rt; {
 *    // ....
 * }
 * </pre>
 * 2. 宣告一組有意義的PK的getter/setter，提供方便的存取。<br/>
 * 如：<br/>
 * <pre>
 * &#64;Entity
 * &#64;Table(name = "SubdivForm")
 * &#64;AttributeOverride(name = "poid", column = &#64;Column(name = "FormCode", length = 8, nullable = false))
 * public class SubdivisionForm extends PersistentObject&lt;String&rt; { 
 *    // ....
 *    
 *    &#64;Transient
 *    public String getFormCode() {
 *    	return getPoid();
 *    }
 *    
 *    public void setFormCode(String code) {
 *    	setPoid(code);
 *    }
 * }<br/>
 * </pre>
 * </p>
 * 
 * 
 * @param <K>
 *            poid的型別，因為使用natural key作為poid，因此實作時必須自行指定poid的型別。
 *            <p>
 *            例如 檢驗單的PK為 檢驗單代號，
 *            </p>
 */
@MappedSuperclass
public abstract class SingleNaturalKeyObject<K extends Serializable> extends
		PersistentObject<K> {

	private K poid;
	
	@Id
	@Override
	public K getPoid() {
		return poid;
	}

	@Override
	public void setPoid(K poid) {
		this.poid = poid;
	}

}
