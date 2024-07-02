package com.zhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.reggie.dto.SetmealDto;
import com.zhao.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联信息
     */
    public void saveWithDish(SetmealDto setmealDto);
    /**
     * 删除套餐
     * @param ids
     */
    public void deleteWithDish(List<Long> ids);

}
