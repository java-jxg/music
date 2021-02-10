package com.fastcase.service.impl;

import com.fastcase.entity.Admin;
import com.fastcase.mapper.AdminMapper;
import com.fastcase.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author Reg
 * @since 2021-01-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
