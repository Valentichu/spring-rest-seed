package com.valentichu.server.core.controller;

import com.valentichu.server.base.value.ResultGenerator;
import com.valentichu.server.core.domain.Good;
import com.valentichu.server.core.value.Page;
import com.valentichu.server.base.value.Result;
import com.valentichu.server.core.service.GoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "商品相关的服务",description = "商品相关的服务")
public class GoodController {
    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ApiOperation(value = "获取商品列表",notes = "获取商品列表 ")
    public Result goods(@RequestParam(required = false, name = "page")@ApiParam("页数") Integer page,
                        @RequestParam(required = false, name = "pageSize")@ApiParam("页面显示条数") Integer pageSize,
                        @RequestParam(required = false, name = "priceLevel")@ApiParam("价格区间") Integer priceLevel,
                        @RequestParam(required = false, name = "sort")@ApiParam("排序") Integer sort) {
        List<Good> goodList = goodService.listGoods(page, pageSize, priceLevel, sort);
        Page data = new Page();
        data.setList(goodList);
        return ResultGenerator.genSuccessResult(data);
    }
}
