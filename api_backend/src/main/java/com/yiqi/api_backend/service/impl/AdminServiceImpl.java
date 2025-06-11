package com.yiqi.api_backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiqi.api_backend.entity.Admin;
import com.yiqi.api_backend.mapper.AdminMapper;
import com.yiqi.api_backend.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    public AdminMapper adminMapper;

    @Override
    public Page<Admin> getPage(Page<Object> page, QueryWrapper<Admin> adminQueryWrapper) {
        return adminMapper.getPage(page, adminQueryWrapper);
    }
}




