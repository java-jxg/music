import {get,post} from './http'

export const getLoginStatus = (params) => post('admin/login/status',params)
