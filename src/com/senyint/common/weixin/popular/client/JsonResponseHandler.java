package com.senyint.common.weixin.popular.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.senyint.common.weixin.popular.util.JsonUtil;

public class JsonResponseHandler{

	public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz){
		return new ResponseHandler<T>() {
			@Override
			public T handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String str = EntityUtils.toString(entity);
                    return JsonUtil.parseObject(new String(str.getBytes("iso-8859-1"),"utf-8"), clazz);
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
			}
		};
	}

}
