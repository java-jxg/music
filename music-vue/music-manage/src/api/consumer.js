import {get,post,put,del} from './http'

//查询歌手
export const getAllConsumer =() => get(`consumer/allConsumer`);

//添加歌手
export const addConsumer = (params) => post('consumer/addConsumer',params)

//修改歌手
export const editConsumer = (params) => put('consumer/updateConsumer',params)

//修改歌手
export const deleteConsumer = (id) => del(`consumer/deleteConsumer/${id}`)
