package com.valentichu.server.core.controller;

import com.valentichu.server.base.exception.ServiceException;
import com.valentichu.server.base.value.Result;
import com.valentichu.server.base.value.ResultGenerator;
import com.valentichu.server.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关API控制器
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@RestController
@Api(value = "用户相关的API", description = "用户相关的API")
@RequestMapping(value = "/user")
public class UserController {
    @Value("${jwt.header}")
    private String header;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
