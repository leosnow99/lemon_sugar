package com.surgar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
	//有效期
	public static final Long JWT_TTL = 3600000L;
	
	//jwt令牌信息
	public static final String JWT_KEY = "changgou_leo";
	
	public static String createJWT(String id, String subject, Long ttlMillis) {
		//指定算法
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		//当前系统时间
		final long times = System.currentTimeMillis();
		
		//令牌签发时间
		final Date now = new Date(times);
		
		//如果令牌有效期为null, 默认值为1小时
		if (ttlMillis == null) {
			ttlMillis = JWT_TTL;
		}
		
		//令牌过期时间设置
		long expMillis = times + ttlMillis;
		Date expDate = new Date(expMillis);
		
		//生成密钥
		final SecretKey secretKey = generalKey();
		
		//封装令牌信息
		final JwtBuilder jwtBuilder = Jwts.builder()
				.setId(id)
				.setSubject(subject)
				.setIssuer("admin")
				.setIssuedAt(now)
				.setExpiration(expDate)
				.signWith(signatureAlgorithm, secretKey);
		return jwtBuilder.compact();
		
		
	}
	
	public static SecretKey generalKey() {
		final byte[] encode = Base64.getEncoder().encode(JwtUtil.JWT_KEY.getBytes());
		return new SecretKeySpec(encode, 0, encode.length, "AES");
	}
	
	public static Claims parseJWT(String jwt) {
		final SecretKey secretKey = generalKey();
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody();
	}
}
