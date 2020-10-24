package com.bibe.crm.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class BasePage {
    /**
     * 分页查询的参数，当前页数
     */
    private long current=1;
    /**
     * 分页查询的参数，当前页面每页显示的数量
     */
    private long size=15;


    /**
     * 从form中获取page参数，用于分页查询参数
     *
     * @return
     */
    public Page getPage() {
        return new Page(this.getCurrent(), this.getSize());
    }

}
