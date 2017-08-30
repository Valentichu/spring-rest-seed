package com.valentichu.server.core.controller;

import com.valentichu.server.common.value.ResultGenerator;
import com.valentichu.server.core.domain.Good;
import com.valentichu.server.core.value.Page;
import com.valentichu.server.common.value.Result;
import com.valentichu.server.core.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品相关API控制器
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@RestController
@Api(value = "商品相关的API", description = "商品相关的API")
public class GoodController {
    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品列表",notes = "获取商品列表 ")
    public Result listGoods(@RequestParam(required = false, name = "page") @ApiParam("页数") Integer page,
                            @RequestParam(required = false, name = "pageSize") @ApiParam("页面显示条数") Integer pageSize,
                            @RequestParam(required = false, name = "priceLevel") @ApiParam("价格区间") Integer priceLevel,
                            @RequestParam(required = false, name = "sort") @ApiParam("排序") Integer sort) {
        List<Good> goodList = goodService.listGoods(page, pageSize, priceLevel, sort);
        Page data = new Page();
        data.setList(goodList);
        return ResultGenerator.genSuccessResult(data);
    }
}
