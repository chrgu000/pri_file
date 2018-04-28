package com.gzjky.constant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzjky.bean.Result;
/**
 * 常量和常用方法集合
 * @author gzjky
 *
 */
public class Constant {
	public static final String HTTP_HEADER = "http:/";
	/**
	 * redis 获取公钥键
	 */
	public static final String SERVICE_PUBLIC_KEY = "servicePublicKey";
	/**
	 * redis 获取私钥键
	 */
	public static final String SERVICE_PRIVATE_KEY = "servicePrivateKey";
	/**
	 * 获取公钥的请求路径
	 */
	public static final String GET_RSA_PUBLIC_PATH = "/publicmanage/PublicService/getPublicKey";
	/**
	 * 获取参数的键
	 */
	public static final String GET_PARAM = "param";
	/**
	 * 服务器北部错误json
	 */
	public static final String JSONP_SERVER_IS_ERROR = "callback({\"code\":\"500\",\"msg\":\"15\",\"obj\":\"服务器响应错误，请联系具体开发人员敬请谅解，我们正在加油\"});";
	/**
	 * 参数错误json
	 */
	public static final String JSONP_PARAM_IS_ERROR = "callback({\"code\":\"500\",\"msg\":\"15\",\"obj\":\"服务器解析json错误敬请谅解，我们正在加油\"});";
	/**
	 * 不需要AES加密的路径
	 */
	public static final String[] NO_AES_PATHS = {};
	/**
	 * 需要rsa解密的路径
	 */
	private static final String[] DECRYPT_RSA_PATHS = { "/userloginservice/UserLoginManager/getLogin" };

	/**
	 * 检查该路径是否需要RSA解密
	 * 
	 * @param path
	 * @return 需要返回 true 不需要返回false
	 */
	public static boolean checkIsDecryptRsaPath(String path) {
		boolean havePath = false;
		for (String rsaPathString : DECRYPT_RSA_PATHS) {
			if (rsaPathString.equals(path)) {
				havePath = true;
				break;
			}
		}
		return havePath;
	}

	/**
	 * 获取返回信息
	 * 
	 * @param code
	 * @param msg
	 * @param obj
	 * @return
	 */
	public static String creatResponseJsonp(Integer code, String msg, Object obj) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		result.setObj(obj);
		try {
			ObjectMapper mapper = new ObjectMapper();
			String errorRst = mapper.writeValueAsString(result);
			StringBuffer sb = new StringBuffer("callback");
			sb.append("(");
			sb.append(errorRst);
			sb.append(");");
			return sb.toString();
		} catch (Exception e) {

		}
		return null;
	}
	/**
	 * 任意类型转json
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转jsonp
	 * 
	 * @param json
	 * @return
	 */
	public static String jsonToJsonp(String json) {
		StringBuffer sb = new StringBuffer("callback");
		sb.append("(");
		sb.append(json);
		sb.append(");");
		return sb.toString();
	}

	/**
	 * 查看字符串是否为空
	 * 
	 * @param value
	 * @return 空返回true 非空返回false
	 */
	public static boolean isBlank(String value) {
		if (value == null || "".equals(value) || value.length() == 0) {
			return true;
		}
		return false;
	}
}
