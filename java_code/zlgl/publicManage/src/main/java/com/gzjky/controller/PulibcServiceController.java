package com.gzjky.controller;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gzjky.bean.Message;
import com.gzjky.constant.Constant;
//import com.gzjky.framework.RSAGJK;


@RestController
@RequestMapping("PublicService")
public class PulibcServiceController {  
	
	@Resource 
	private RedisTemplate<Serializable,Serializable> redisTemplate; 
	/**
	 * 获取公钥
	 * @return
	 */
	@RequestMapping("getPublicKey")
	public Message getPublicKey() {
		return new Message(200, "获取成功", redisTemplate.opsForValue().get(Constant.SERVICE_PUBLIC_KEY));
	}
    @PostConstruct
    public void setRsaPrivateKeyAndPublicKey() {
//    	KeyPair keyPair = RSAGJK.generateRSAKeyPair(1024);
//    	String publicKey = RSAGJK.getPublicKeyString((RSAPublicKey)keyPair.getPublic());
//    	String privateKey = RSAGJK.getPrivateKeyString((RSAPrivateKey)keyPair.getPrivate());
//    	redisTemplate.opsForValue().set(Constant.SERVICE_PUBLIC_KEY, publicKey);
//    	redisTemplate.opsForValue().set(Constant.SERVICE_PRIVATE_KEY, privateKey);
//    	System.out.println(redisTemplate.opsForValue().get(Constant.SERVICE_PUBLIC_KEY));
    }
}
