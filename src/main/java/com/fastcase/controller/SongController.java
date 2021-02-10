package com.fastcase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fastcase.entity.Consumer;
import com.fastcase.entity.Singer;
import com.fastcase.entity.Song;
import com.fastcase.hadler.XGException;
import com.fastcase.service.SongService;
import com.fastcase.utils.MUtils;
import com.fastcase.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 歌曲 前端控制器
 * </p>
 *
 * @author Reg
 * @since 2021-01-27
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("addSongFile")
    public R addSongFile(MultipartFile file){
        String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        boolean b = MUtils.uploadFile(file,fileName, path);
        return b ? R.ok("上传成功").data("fileName","/song/"+fileName) : R.error("上传失败");
    }


    @PostMapping("addSong")
    public R addSong(@RequestBody Song song){
        song.setPic("/img/songPic/tubiao.jpg");
        boolean save = songService.save(song);
        return save ? R.ok("上传成功") : R.error("上传失败");

    }

    @GetMapping("getSongById/{singerId}")
    public R songOfSingerId(@PathVariable String singerId){
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("singer_id",singerId);
        List<Song> songList = songService.list(wrapper);
        if(songList == null) return R.error("查询失败");
        return R.ok().data("songList",songList);
    }

    @PutMapping("updateSong")
    public R updateSong(@RequestBody Song song){
        boolean b = songService.updateById(song);
        return b ? R.ok() : R.error();
    }

    @DeleteMapping("deleteSong/{id}")
    public R deleteSong(@PathVariable String id){
        boolean b = songService.removeById(id);
        return b ? R.ok("删除成功") : R.error("删除失败");
    }

    /**
     * 修改图片地址
     */
    @PostMapping("updateSongPic")
    public R updateSongPic(@RequestParam("file") MultipartFile file, @RequestParam("id")int id){

        String path = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        boolean b = MUtils.uploadFile(file,fileName, path);

        if(b){
            String storeAvatorPath = "/img/songPic/"+fileName;
            Song song = new Song();
            song.setPic(storeAvatorPath);
            song.setId(id);
            boolean result = songService.updateById(song);
            return result ? R.ok() : R.error();
        }else{
            return R.error("上传图片失败");
        }
    }


    @GetMapping("allSong")
    public R allSong(){
        List<Song> list = songService.list();
        if(list.size()==0) throw new XGException(20001,"查询失败");
        return R.ok().data("songList",list);
    }

    @GetMapping("likeSongOfName/{keyword}")
    public R likeSongOfName(@PathVariable String keyword){
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.like("name",keyword);
        List<Song> list = songService.list(wrapper);
        return R.ok().data("songList",list);
    }

    @GetMapping("getSongByKId/{id}")
    public R getSongByKId(@PathVariable String id){
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Song byId = songService.getById(id);
        return R.ok().data("song",byId);
    }
}

