package com.valentichu.server.security.interceptor;

import com.valentichu.server.security.util.CookieUtil;
import com.valentichu.server.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验Token的拦截器
 */
@Component
public class TokenValidateInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.enableCookie}")
    private boolean enableCookie;

    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final CookieUtil cookieUtil;

    @Autowired
    public TokenValidateInterceptor(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, CookieUtil cookieUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.cookieUtil = cookieUtil;
    }

    //先校验Header中的Token,再校验Cookie中的Token,如果都未通过抛出异常
    //只要能从Token中读出有效信息即为通过校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UsernamePasswordAuthenticationToken authentication = getAuthenticationFromHeader(request);
        if (authentication != null) {
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }

        authentication = getAuthenticationFromCookie(request);
        if (authentication != null) {
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } else {
            cookieUtil.removeCookie(header, "/", response); //如果Cookie里面的Token校验失败，删除作废的Cookie
        }

        throw new BadCredentialsException("Token invalid");
    }

    //校验Header中的Token
    private UsernamePasswordAuthenticationToken getAuthenticationFromHeader(HttpServletRequest request) {
        final String token = request.getHeader(header);
        return getAuthenticationFromToken(token);
    }

    //校验Cookie中的Token
    private UsernamePasswordAuthenticationToken getAuthenticationFromCookie(HttpServletRequest request) {
        //在允许Cookie的时候才校验
        if (!enableCookie) {
            return null;
        }
        final String token = cookieUtil.getValue(header, request);
        return getAuthenticationFromToken(token);
    }

    //尝试从Token中取出身份信息
    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token) {
        if (token != null && token.startsWith(tokenHead)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (username != null) {
                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，因为需要从数据库中读取权限，所以还是通过Spring Security的 @UserDetailsService 进行了数据查询
                // 但简单验证的话，可以采用直接验证token是否合法来避免昂贵的数据查询
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}