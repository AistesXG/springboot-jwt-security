package com.sys.bill.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Title: JwtTokenUtils
 * @Description: jwt token 的工具类
 * @author: furg@senthink.com
 * @date: 2019/8/27 14:12
 */
@Component
public class JwtTokenUtils implements Serializable {

    private static final String AUDIENCE = "web";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.prefix}")
    private String tokenPrefix;


    /**
     * 从token中解析出username
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    /**
     * 从请求头中解析出username
     *
     * @param authorizationHeader
     * @return
     */
    public String getUsernameFromAuthtoizationHeader(String authorizationHeader) {
        String token = authorizationHeader.substring(tokenPrefix.length());
        return getUsernameFromToken(token);
    }

    /**
     * 从token中解析出token的过期时间
     *
     * @param token
     * @return
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * 从token中解析出token的过期时间
     *
     * @param token token
     * @return date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从token中解析出目标受众
     *
     * @param token
     * @return
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }


    /**
     * 生成token
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(10);
        return doGenerateToken(claims, userDetails.getUsername(), AUDIENCE);
    }


    /**
     * JWT TOKEN 生成
     *
     * @param claims
     * @param subject
     * @param audience
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
        final Date createDate = new Date();
        final Date expirationDate = calculateExpirationDate(createDate);

        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }


    /**
     * 判断token是否能被刷新
     *
     * @param token
     * @param lastPwdReset
     * @return
     */
    public boolean canTokenBeRefreshed(String token, Date lastPwdReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPwdReset(created, lastPwdReset) && !isTokenExpired(token);
    }


    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }


    /**
     * 校验token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        CurrentUser user = (CurrentUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date createdDate = getIssuedAtDateFromToken(token);


        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPwdReset(createdDate, user.getUser().getLastPwdRestDate());
    }

    /**
     * 计算token过期时间
     *
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    /**
     * 解析声明数据
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    /**
     * 从token中获取所有的声明数据
     *
     * @param token
     * @return
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 判断token是否是在重置密码之前创建的
     *
     * @param created
     * @param lastPwdReset
     * @return
     */
    private boolean isCreatedBeforeLastPwdReset(Date created, Date lastPwdReset) {
        return (lastPwdReset != null && created.before(lastPwdReset));
    }
}
