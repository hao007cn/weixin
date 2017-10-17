package com.senyint.core.entity.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * base class for persistent entity class that using surrogate key as primary
 * key. the property <code>poid</code> is the surrogate key column, and value
 * should be generated automatically.
 * <p>
 * 直接繼承這個類別，並實作Entity應有的屬性。poid與id mapping會自動處理。
 * </p>
 * 
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class SurrogateKeyObject extends PersistentObject<Integer> {

	private static final long serialVersionUID = 1L;
	private Integer poid;
	
	/**
	 * 
	 * @see com.inqgen.iqlis.persistence.PersistentObject#getPoid()
	 */
	@Id
	@TableGenerator(name="id",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="id")
	@Column(name="id")
	@Override
	public Integer getPoid() {
		return poid;
	}

	@Override
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
}
