package com.valentichu.server.common.value;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

/**
 * 统一API响应结果封装
 *
 * @author Valentichu
 * created on 2017/08/25
 */
public class Result implements Serializable {
    private static final long serialVersionUID = -4686581693780800154L;

    private Integer code;
    private String message;
    private Object data;

    public Result setCode(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
