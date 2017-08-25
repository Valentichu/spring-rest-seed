package com.valentichu.server.core.service;

import com.valentichu.server.core.domain.Good;
import java.util.List;

/**
 * 商品Service的定义
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public interface GoodService {
    /**
     * 列出商品
     *
     * @param page       页数
     * @param pageSize   每页显示条数
     * @param priceLevel 价格区间等级
     * @param sort       排序方法
     * @return 商品列表
     */
    List<Good> listGoods(Integer page, Integer pageSize, Integer priceLevel, Integer sort);
}
