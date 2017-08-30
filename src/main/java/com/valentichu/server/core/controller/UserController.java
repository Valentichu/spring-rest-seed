package com.valentichu.server.core.controller;

import com.valentichu.server.common.value.Result;
import com.valentichu.server.common.value.ResultGenerator;
import com.valentichu.server.core.service.UserService;
import com.valentichu.server.core.util.RequestUtils;
import com.valentichu.server.core.value.CartInfo;
import com.valentichu.server.core.value.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    private final UserService userService;
    private final RequestUtils requestUtils;

    @Autowired
    public UserController(UserService userService, RequestUtils requestUtils) {
        this.userService = userService;
        this.requestUtils = requestUtils;
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ApiOperation(value = "加入购物车", notes = "加入购物车 ")
    public Result addCart(@RequestBody @ApiParam("购物车信息，包含加入购物车的商品Id") CartInfo cartInfo,
                          HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        userService.addCart(userName, cartInfo.getProductId());
        return ResultGenerator.genSuccessResult();
    }
}
