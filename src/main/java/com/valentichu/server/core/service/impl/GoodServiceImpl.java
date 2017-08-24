package com.valentichu.server.core.service.impl;

import com.valentichu.server.core.service.GoodService;
import com.valentichu.server.core.mapper.GoodMapper;
import com.valentichu.server.core.domain.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    private final GoodMapper goodMapper;

    @Autowired
    public GoodServiceImpl(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
    }

    @Override
    public List<Good> listGoods(Integer page, Integer pageSize, Integer priceLevel, Integer sort) {
        Integer skip = null;
        Integer limit = null;
        Integer max = null;
        Integer min = null;
        String sortFlag = null;

        if (page != null && pageSize != null) {
            limit = pageSize;
            skip = (page - 1) * pageSize;
        }

        if (priceLevel != null) {
            switch (priceLevel) {
                case 0:
                    min = 0;
                    max = 100;
                    break;
                case 1:
                    min = 100;
                    max = 500;
                    break;
                case 2:
                    min = 500;
                    max = 1000;
                    break;
                case 3:
                    min = 1000;
                    max = 5000;
                    break;
            }
        }

        if (sort != null) {
            if (sort == 1) {
                sortFlag = "ASC";
            } else {
                sortFlag = "DESC";
            }
        }

        return goodMapper.listGoods(skip, limit, max, min, sortFlag);
    }
}
