package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Comment;
import com.fastcase.entity.Comment;
import com.fastcase.hadler.XGException;
import com.fastcase.service.CommentService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("addComment")
    public R addComment(@RequestBody Comment comment){
        boolean save = commentService.save(comment);
        return save ? R.ok() : R.error();
    }
    @PutMapping("updateComment")
    public R updateComment(@RequestBody Comment comment){
        boolean b = commentService.updateById(comment);
        return b ? R.ok() : R.error();
    }
    @DeleteMapping("deleteComment/{id}")
    public R deleteComment(@PathVariable String id){
        boolean b = commentService.removeById(id);
        return b ? R.ok() : R.error();
    }

    //根据歌曲id查询
    @GetMapping("selectBySongId/{songId}")
    public R selectBySongId(@PathVariable String songId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("song_id",songId);
        List<Comment> list = commentService.list(wrapper);
        return R.ok().data("comments",list);
    }
    //根据歌单id查询
    @GetMapping("selectBySongListId/{songListId}")
    public R selectBySongListId(@PathVariable String songListId){
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id",songListId);
        List<Comment> list = commentService.list(wrapper);
        return R.ok().data("comments",list);
    }

}

