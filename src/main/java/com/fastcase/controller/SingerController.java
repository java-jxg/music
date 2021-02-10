package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Admin;
import com.fastcase.entity.Singer;
import com.fastcase.hadler.XGException;
import com.fastcase.service.SingerService;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 歌手 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-23
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    /**.
     * 添加歌手
     */
    @PostMapping("addSigner")
    public R addSigner(@RequestBody Singer singer){
        boolean save = singerService.save(singer);
        return save ? R.ok() : R.error();
    }
    @PutMapping("updateSigner")
    public R updateSigner(@RequestBody Singer signer){
        boolean b = singerService.updateById(signer);
        return b ? R.ok() : R.error();
    }
    @DeleteMapping("deleteSigner/{id}")
    public R deleteSigner(@PathVariable String id){
        boolean b = singerService.removeById(id);
        return b ? R.ok() : R.error();
    }
    @GetMapping("selectByPrimaryKey/{id}")
    public R selectByPrimaryKey(@PathVariable String id){
        Singer singer = singerService.getById(id);
        if(singer == null) throw new XGException(20001,"查询失败");
        return R.ok().data("signer",singer);
    }

    @GetMapping("allSinger")
    public R allSigner(){
        List<Singer> list = singerService.list();
        if(list.size()==0) throw new XGException(20001,"查询失败");
        return R.ok().data("singerList",list);
    }
    /**
     * 根据歌手名字模糊查询
     */
    @PostMapping("signerOfName")
    public R signerOfName(@RequestBody Singer singer){
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.like("name",singer.getName());
        List<Singer> list = singerService.list(wrapper);
        if(list.size()==0) throw new XGException(20001,"查询失败");
        return R.ok().data("signerList",list);
    }
    /**
     * 根据歌手名字模糊查询
     */
    @GetMapping("signerOfSex/{sex}")
    public R signerOfSex(@PathVariable String sex){
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.eq("sex",sex);
        List<Singer> list = singerService.list(wrapper);
        if(list.size()==0) throw new XGException(20001,"查询失败");
        return R.ok().data("signerList",list);
    }
    /**
     * 修改图片地址
     */
    @PostMapping("updateSingerPic")
    public R updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        if(avatorFile.isEmpty()) return R.error().message("文件上传失败");
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"singerPic";
        File f = new File(filePath);
        if(!f.exists()){
            f.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/singerPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setPic(storeAvatorPath);
            singer.setId(id);
            boolean b = singerService.updateById(singer);
            return b ? R.ok().message("上传成功") : R.error().message("上传失败");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("上传出错");
        }

    }


}

