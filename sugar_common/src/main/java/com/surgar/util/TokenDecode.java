package com.surgar.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TokenDecode {
	//公钥
	private static final String PUBLIC_KEY = "public.key";
	
	private static String publicKey = "";
	
	
	//获取非对称加密公钥 Key
	public String getPubKey() {
		if (!StringUtils.isEmpty(publicKey)) {
			return publicKey;
		}
		Resource resource = new ClassPathResource(PUBLIC_KEY);
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
			BufferedReader br = new BufferedReader(inputStreamReader);
			publicKey = br.lines().collect(Collectors.joining("\n"));
			return publicKey;
		} catch (IOException ioe) {
			return null;
		}
	}
	
	//读取令牌数据
	public Map<String, String> decodeToken(String token) {
		//校验Jwt
		final Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));
		//获取token原始内容
		final String claims = jwt.getClaims();
		return JSON.parseObject(claims, Map.class);
	}
	
	public Map<String, String> getUserInfo() {
		//获取授权信息
		final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder
				.getContext().getAuthentication().getDetails();
		//令牌解码
		return decodeToken(details.getTokenValue());
	}
}
