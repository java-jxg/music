import {get,post,put,del} from './http'

//查询歌手
export const getAllSinger =() => get(`singer/allSinger`);

//添加歌手
export const addSigner = (params) => post('singer/addSigner',params)

//修改歌手
export const editSigner = (params) => put('singer/updateSigner',params)

//修改歌手
export const deleteSigner = (id) => del(`singer/deleteSigner/${id}`)

