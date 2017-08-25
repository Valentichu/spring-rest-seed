package com.valentichu.server.core.mapper;

import com.valentichu.server.core.domain.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品相关数据库接口
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public interface GoodMapper {
    /**
     * 从数据库中取出商品信息
     *
     * @param skip  跳过条数
     * @param limit 取出条数
     * @param max   最大价格
     * @param min   最小价格
     * @param sort  排序方法
     * @return 商品列表
     */
    List<Good> listGoods(@Param("skip") Integer skip,
                         @Param("limit") Integer limit,
                         @Param("max") Integer max,
                         @Param("min") Integer min,
                         @Param("sort") String sort);
}

