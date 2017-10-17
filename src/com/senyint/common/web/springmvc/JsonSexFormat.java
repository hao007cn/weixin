/**
 * JsonSexFormat.java
 * com.senyint.common.web.springmvc
 * Function: TODO ADD FUNCTION
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2014年12月12日 		gjp
 *
 * Copyright (c) 2014, senyint All Rights Reserved.
*/
/**
* @File: JsonSexFormat.java
* @Package com.senyint.common.web.springmvc
* @Description: TODO Describe the File
*
* @Company: senyint (Dalian) Co., Ltd
* @author   gjp
* @date     2014年12月12日
* @version  1.0
*/

package com.senyint.common.web.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.senyint.core.entity.component.SexType;

/**
 * ClassName:JsonSexFormat
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   gjp
 * @version  
 * @since    Ver 1.1
 * @Date	 2014年12月12日
 *
 * @see 	 
 */
/**
 * @Type: JsonSexFormat
 * @Description: TODO  Describe the Type
 *
 * @Company: senyint (Dalian) Co., Ltd
 * @author   gjp
 * @date     2014年12月12日
 * @version  1.0
 *
 */
public class JsonSexFormat extends JsonSerializer<SexType> {
	private static final String MAN = "男";
	private static final String WOMAN="女";
	private static final String SECRET="保密";
	@Override
	public void serialize(SexType sexType, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		
		if(sexType.equals(SexType.MALE))
		{
			gen.writeString(MAN);
		}
		if(sexType.equals(SexType.FEMALE))
		{
			gen.writeString(WOMAN);
		}
		if(sexType.equals(SexType.Unknown))
		{
			gen.writeString(SECRET);
		}
		
	}
}
