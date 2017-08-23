package com.valentichu.server.core.mapper;

import com.valentichu.server.core.domain.Good;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodMapper {

    List<Good> listGoods(@Param("skip") Integer skip,
                         @Param("limit") Integer limit,
                         @Param("max") Integer max,
                         @Param("min") Integer min,
                         @Param("sort") String sort);
}

