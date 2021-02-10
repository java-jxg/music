package com.fastcase.service.impl;

import com.fastcase.entity.Comment;
import com.fastcase.mapper.CommentMapper;
import com.fastcase.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Reg
 * @since 2021-02-06
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
