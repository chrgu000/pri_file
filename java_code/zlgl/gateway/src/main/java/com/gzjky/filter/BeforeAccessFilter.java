package com.gzjky.filter;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzjky.bean.Result;
import com.gzjky.bean.UserToken;
import com.gzjky.constant.Constant;
//import com.gzjky.framework.AESGJK;
//import com.gzjky.framework.RSAGJK;

public class BeforeAccessFilter extends ZuulFilter {
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	@Autowired
	RestTemplate restTemplate;

	@Override
	public String filterType() {
		return "pre";// pre在请求被路由之前调用，routing在路由被请求时候调用，post在routing和error过滤器之后被调用，error处理请求发生错误时被调用
	}

	@Override
	public int filterOrder() {
		return 0;// 过滤器的执行顺序
	}

	@Override
	public boolean shouldFilter() {
		return true;// 定义该过滤器是否执行 true是执行 false是不执行
	}

	@Override
	public Object run() {// 过滤器的具体逻辑
//		// 获取请求容器
//		RequestContext ctx = this.getRequestContext();
//		// 获取请求体
//		HttpServletRequest request = ctx.getRequest();
//		// 获取请求路径
//		String requstPath = request.getServletPath().toString();
//		ctx.setSendZuulResponse(false);// false 不进行路由 true继续进行路由
//		// 获取公钥
//		if (Constant.GET_RSA_PUBLIC_PATH.equals(requstPath)) {
//			ResponseEntity<Result> response = restTemplate.postForEntity(Constant.HTTP_HEADER + requstPath,
//					this.getRequestParam(null, null), Result.class);
//			Result result = response.getBody();
//			String responseBody = Constant.objToJson(result);
//			ctx.setResponseBody(!Constant.isBlank(responseBody) ? Constant.jsonToJsonp(responseBody)
//					: Constant.JSONP_SERVER_IS_ERROR);
//			return null;
//		}
//		// 拿到参数
//		String jsonObject = request.getParameter(Constant.GET_PARAM);
//		// 如果参数为空直接返回错误码并且 不进行路由
//		if (jsonObject == null) {
//			String response = Constant.creatResponseJsonp(400, "10", "参数不能为空");
//			ctx.setResponseBody(!Constant.isBlank(response) ? response : Constant.JSONP_PARAM_IS_ERROR);// 返回参数不能为空错误码
//			return null;
//		}
//		// 加密字符串转义
//		jsonObject = jsonObject.replaceAll(" ", "+");
//		try {
//			if (Constant.checkIsDecryptRsaPath(requstPath)) {
//				String privateRsaKey = redisTemplate.opsForValue().get(Constant.SERVICE_PRIVATE_KEY).toString();
//				String deciphering = RSAGJK.decryptByPrivateKey(jsonObject, privateRsaKey);
//				ResponseEntity<Result> response = restTemplate.postForEntity(Constant.HTTP_HEADER + requstPath,
//						this.getRequestParam(deciphering, null), Result.class);
//				Result result = response.getBody();
//				String responseBody = Constant.objToJson(result);
//				ctx.setResponseBody(!Constant.isBlank(responseBody) ? Constant.jsonToJsonp(responseBody)
//						: Constant.JSONP_SERVER_IS_ERROR);
//				return null;
//			}
//			if (jsonObject.length() < 32) {
//				String response = Constant.creatResponseJsonp(400, "10", "参数长度不对");
//				ctx.setResponseBody(!Constant.isBlank(response) ? response : Constant.JSONP_PARAM_IS_ERROR);// 返回参数不能为空错误码
//				return null;
//			}
//			String token = jsonObject.substring(0, 32);
//			String paramJsonStr = jsonObject.substring(32);
//			UserToken ut = (UserToken) redisTemplate.opsForValue().get(token);
//
//			if (ut != null) {
//				ResponseEntity<Result> response = restTemplate.postForEntity(Constant.HTTP_HEADER + requstPath,
//						this.getRequestParam(AESGJK.aesDecrypt(paramJsonStr, ut.getAesPrivateKey()), ut.getUserId()),
//						Result.class);
//				Result result = response.getBody();
//				if (result == null) {
//					ctx.setResponseBody(Constant.JSONP_SERVER_IS_ERROR);
//				} else {
//					String objJson = Constant.objToJson(result.getObj());
//					ctx.setResponseBody(Constant.creatResponseJsonp(result.getCode(), result.getMsg(),
//							AESGJK.aesEncrypt(objJson, ut.getAesPrivateKey())));
//
//				}
//				return null;
//			} else {
//				String response = Constant.creatResponseJsonp(403, "11", "token验证错误");
//				ctx.setResponseBody(!Constant.isBlank(response) ? response : Constant.JSONP_PARAM_IS_ERROR);// 返回参数不能为空错误码
//				return null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String response = Constant.creatResponseJsonp(500, "15", "服务器未知错误");
//		ctx.setResponseBody(!Constant.isBlank(response) ? response : Constant.JSONP_PARAM_IS_ERROR);
		return null;
	}

	/**
	 * 获取请求参数
	 * 
	 * @return
	 */
	private HttpEntity<MultiValueMap<String, String>> getRequestParam(String paramStr, String userId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

			ObjectMapper mapper = new ObjectMapper();
			if (!Constant.isBlank(paramStr)) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = mapper.readValue(paramStr, Map.class);
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					params.add(entry.getKey(), entry.getValue() + "");
				}
			}
			params.add("userId", userId);
			return new HttpEntity<MultiValueMap<String, String>>(params, headers);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 获取请求容器
	 * 
	 * @return
	 */
	private RequestContext getRequestContext() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.getResponse().setCharacterEncoding("UTF-8");
		ctx.getResponse().setContentType("text/html;charset=UTF-8");
		ctx.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return ctx;
	}
}
