package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string,string->string,bigint,string,bigint,string,string,string,string,string,bigint,string,string"})
public class GetRecommendItemClick extends UDTF {

    @Override
    public void setup(ExecutionContext ctx) throws UDFException {

    }

    @Override
    public void process(Object[] args) throws UDFException {
        String url_parm = (String) args[0];
        String access_time = (String) args[1];
        String[] parms = url_parm.split("\\&");
        long uid = 0;
        String path = null;
        String app_id = null;
        String platform = null;
        long brand_area_id = 0;
        String iid = null;
        String action = null;
        String origin = null;
        long channelid = 0;
        String device_id = null;
        String version = null;
        for(String parm : parms){
            if(parm.contains("myuid")||parm.contains("userid")){
                String[] uids = parm.split("=");
                if(uids.length > 1){
                    String myuid = uids[1];
                    try{
                        uid = Long.parseLong(myuid);
                    }catch (Exception e){
                        uid = 0;
                    }
                }
            }else if(parm.contains("path")){
                String[] paths = parm.split("=");
                if(paths.length > 1){
                    path = paths[1];
                }
            }else if(parm.contains("app_id")){
                String[] app_ids = parm.split("=");
                if(app_ids.length > 1){
                    String appId = app_ids[1];
                    app_id = appId;
                }
            }else if(parm.contains("item_id")){
                String[] item_ids = parm.split("=");
                if(item_ids.length > 1){
                    String item_id = item_ids[1];
                    if(item_id.contains(" HTTP/1.1")){
                        iid = item_id.replace(" HTTP/1.1","");
                    }else{
                        iid = item_id;
                    }
                }
            }else if(parm.contains("brand_area_id")){
                String[] brand_area_ids = parm.split("=");
                if(brand_area_ids.length > 1) {
                    String bid = brand_area_ids[1];
                    try{
                        brand_area_id = Long.parseLong(bid);
                    }catch (Exception e){
                        brand_area_id = 0;
                    }
                }
            }else if(parm.contains("platform")){
                String[] platforms = parm.split("=");
                if(platforms.length > 1){
                    platform = platforms[1];
                }
            }else if(parm.contains("action")){
                String[] actions = parm.split("=");
                if(actions.length > 1){
                    action = actions[1];
                }
            }else if(parm.contains("origin")){
                String[] origins = parm.split("=");
                if(origins.length > 1){
                    origin = origins[1];
                }
            }else if(parm.contains("channelid")){
                String[] channelids = parm.split("=");
                if(channelids.length > 1){
                    try{
                        channelid = Long.parseLong(channelids[1]);
                    }catch (Exception e){
                        channelid = 0;
                    }
                }
            }else if(parm.contains("device_id")){
                String[] device_ids = parm.split("=");
                if(device_ids.length > 1){
                    device_id = device_ids[1];
                }
            }else if(parm.contains("v=")){
                String[] vs = parm.split("=");
                if(vs.length > 1){
                    version = vs[1];
                }
            }
        }
        forward(access_time,uid,iid,brand_area_id,path,app_id,platform,action,origin,channelid,device_id,version);
    }

    @Override
    public void close() throws UDFException {

    }
}