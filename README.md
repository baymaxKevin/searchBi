# searchBi
## 主要函数说明

| class | odps udf函数 | 说明 | 
| ------ | ------ | ------ |
| GetWoolSearchLog | get_wool_search_log->app_id=23搜索请求解析 | info->(keyword,uid,search_type,platform,search_count,search_result,adzone_id,page_idnex,sort_type,position,app_id,version) |
| GeSearchLog | get_search_log->app_id in (1,2,7)搜索请求日志 | info->(keyword,uid,app_id,post_type,pid_type,platform,search_result) |
| GetBaseEcBehavior | get_base_ec_behavior->app_id in (1,2,7)用户行为日志 | (access_time,url_param,request_body,method)->(method,channel_id,action,platform,request_uri,title,app_id,authorization,brand_area_id,uid,ua,os_version,ot,mac,imei,apn,idfa,adzone_id,version,path,ip,device_id,origin,bundle_id,tbuid,accesss_time,keyword,os,item_id,pid,exposure_time) |

其他函数在相关odps sql中查找
