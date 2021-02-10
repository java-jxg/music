package com.fastcase.service.impl;

import com.fastcase.entity.Song;
import com.fastcase.mapper.SongMapper;
import com.fastcase.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲 服务实现类
 * </p>
 *
 * @author Reg
 * @since 2021-01-27
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

}
