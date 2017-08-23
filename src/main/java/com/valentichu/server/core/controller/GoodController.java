package com.valentichu.server.core.controller;

import com.valentichu.server.base.value.ResultGenerator;
import com.valentichu.server.core.domain.Good;
import com.valentichu.server.core.value.Page;
import com.valentichu.server.base.value.Result;
import com.valentichu.server.core.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodController {

    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CART')")
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    public Result goods(@RequestParam(required = false, name = "page") Integer page,
                        @RequestParam(required = false, name = "pageSize") Integer pageSize,
                        @RequestParam(required = false, name = "priceLevel") Integer priceLevel,
                        @RequestParam(required = false, name = "sort") Integer sort) {
        List<Good> goodList = goodService.listGoods(page, pageSize, priceLevel, sort);
        Page data = new Page();
        data.setList(goodList);
        return ResultGenerator.genSuccessResult(data);
    }
}
