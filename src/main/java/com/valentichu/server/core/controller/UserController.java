package com.valentichu.server.core.controller;

import com.valentichu.server.common.value.Result;
import com.valentichu.server.common.value.ResultGenerator;
import com.valentichu.server.core.domain.CartItem;
import com.valentichu.server.core.service.UserService;
import com.valentichu.server.core.util.RequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/cart/{productId}", method = RequestMethod.POST)
    @ApiOperation(value = "加入购物车", notes = "加入购物车 ")
    public Result addCart(@PathVariable("productId") @ApiParam("加入购物车的商品Id") int productId,
                          HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        userService.addCart(userName, productId);
        return ResultGenerator.genSuccessResult();
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ApiOperation(value = "获取购物车信息", notes = "获取购物车信息")
    public Result listCartItem(HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        List<CartItem> cartItems = userService.listCartItem(userName);
        return ResultGenerator.genSuccessResult(cartItems);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/cart/{productId}", method = RequestMethod.PUT)
    @ApiOperation(value = "更新购物车信息", notes = "更新购物车信息")
    public Result updateCartItem(@PathVariable("productId") @ApiParam("要更新的商品Id") int productId,
                                 @RequestParam("action") @ApiParam("更新数量or选中状态") String action,
                                 @RequestParam("value") @ApiParam("更新的具体值") int value,
                                 HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        userService.updateCartItem(userName, productId, action, value);
        return ResultGenerator.genSuccessResult();
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/cart", method = RequestMethod.PUT)
    @ApiOperation(value = "更新购物车所有项目选中状态", notes = "更新购物车所有项目选中状态")
    public Result updateCartChecked(@RequestParam("checked") @ApiParam("更新的具体值") int checked,
                                    HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        userService.updateCartChecked(userName, checked);
        return ResultGenerator.genSuccessResult();
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/cart/{productId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除购物车中一项商品", notes = "删除购物车中一项商品 ")
    public Result deleteCartItem(@PathVariable("productId") @ApiParam("从购物车中删除的商品Id") int productId,
                                 HttpServletRequest request) {
        String userName = requestUtils.getUserNameFromHeader(request);
        userService.deleteCartItem(userName, productId);
        return ResultGenerator.genSuccessResult();
    }
}
