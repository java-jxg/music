import {get,post,put,del} from './http'
import Axios from "axios";


export const getAllSongList =() => get(`songList/allSongList`);
export const getAllSinger =() => get(`singer/allSinger`);
export const likeSongOfName = (keyword) => get(`song/likeSongOfName/${keyword}`)
export const signUp = (params) => post('consumer/addConsumer',params)
export const loginIn = (params) => post('consumer/loginIn',params)
export const getSongBySongListId = (songListId) => get(`listSong/getSongBySongListId/${songListId}`)

export const download = (url) => Axios({
  method: 'get',
  url: url,
  responseType: 'blob'
})
//评分
export const addRanks = (params) => post('ranks/addRanks',params)
export const average = (songListId) => get(`ranks/average/${songListId}`)
//评论
export const addComment = (params) => post('comment/addComment',params)

//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) => {
  if(type=0){ //歌曲
    return get(`comment/selectBySongId/${id}`)
  }else{//歌单
    return get(`comment/selectBySongListId/${id}`)
  }
}

export const getSongByKId =(id) => get(`song/getSongByKId/${id}`);
export const getConsumerById =(id) => get(`consumer/selectByPrimaryKey/${id}`);
export const updateComment = (params) => put('comment/updateComment',params)
export const getSongById = (singerId) => get(`song/getSongById/${singerId}`)

export const getSongListByTitle =(title) => get(`songList/getSongListByTitle/${title}`);
export const getSongListByStyle =(style) => get(`songList/getSongListByStyle/${style}`);

export const getSingerOfSex =(sex) => get(`singer/signerOfSex/${sex}`);


export const addCollect = (params) => post('collect/addCollect',params)
export const getByUserId =(userId) => get(`collect/getByUserId/${userId}`);


export const updateConsumer = (params) => put('consumer/updateConsumer',params)
