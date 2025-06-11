package com.yiqi.api_backend.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiqi.api_backend.entity.Admin;

public interface AdminService extends IService<Admin> {

    Page<Admin> getPage(Page<Object> page, QueryWrapper<Admin> adminQueryWrapper);
}
