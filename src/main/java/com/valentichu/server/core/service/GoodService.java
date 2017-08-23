package com.valentichu.server.core.service;

import com.valentichu.server.core.domain.Good;

import java.util.List;

public interface GoodService {

    List<Good> listGoods(Integer page, Integer pageSize, Integer priceLevel, Integer sort);
}
