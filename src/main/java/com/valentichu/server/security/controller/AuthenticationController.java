package com.valentichu.server.security.controller;

import com.valentichu.server.base.value.ResultGenerator;
import com.valentichu.server.core.domain.User;
import com.valentichu.server.base.value.Result;
import com.valentichu.server.security.service.AuthenticationService;
import com.valentichu.server.security.util.CookieUtil;
import com.valentichu.server.security.value.Account;
import com.valentichu.server.security.value.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权相关的Controller
 */
@RestController
@RequestMapping(value = "/auth")
@Api(value = "权限相关的服务",description = "权限相关的服务")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.enableCookie}")
    private boolean enableCookie;

    @Value("${jwt.expiration}")
    private Integer expiration;

    private final AuthenticationService authenticationService;

    private final CookieUtil cookieUtil;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, CookieUtil cookieUtil) {
        this.authenticationService = authenticationService;
        this.cookieUtil = cookieUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录",notes = "登录")
    public Result createAuthenticationToken(@RequestBody @ApiParam("用户名和密码") Account account, HttpServletResponse response) throws AuthenticationException {
        final String token = authenticationService.login(account.getUserName(), account.getUserPwd());
        Result result = ResultGenerator.genSuccessResult(new Token(token));
        if (enableCookie) {
            cookieUtil.addCookie(header, token, "/", expiration, response);
        }
        return result;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @ApiOperation(value = "刷新Token",notes = "刷新Token")
    public Result refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        final String oldToken = request.getHeader(header);
        final String refreshedToken = authenticationService.refresh(oldToken);
        return ResultGenerator.genSuccessResult(new Token(refreshedToken));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册",notes = "注册")
    public Result register(@RequestBody @ApiParam("新增的用户") User addedUser) throws AuthenticationException {
        authenticationService.register(addedUser);
        return ResultGenerator.genSuccessResult();
    }
}
