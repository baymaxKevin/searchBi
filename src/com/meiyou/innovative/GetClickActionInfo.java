package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string,string->string,bigint,bigint,string,string,string,string,string"})
public class GetClickActionInfo extends UDTF {

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
        String os = null;
        String iid = null;
        long brand_area_id = 0;
        String order_id = null;
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
            }else if(parm.substring(0,6).equals("app_id")){
                String[] app_ids = parm.split("=");
                if(app_ids.length > 1){
                    String appId = app_ids[1];
                    app_id = appId;
                }
            }else if(parm.contains("platform")){
                String[] platforms = parm.split("=");
                if(platforms.length > 1){
                    os = platforms[1];
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
            }else if(parm.contains("orderIds")){
                String[] order_ids = parm.split("=");
                if(order_ids.length > 1) {
                    String oid = order_ids[1];
                    String[] oids = oid.split(",");
                    for(String od : oids) {
                        order_id = od;
                    }
                }
            }
        }
        forward(access_time,uid,brand_area_id,iid,path,app_id,os,order_id);
    }

    @Override
    public void close() throws UDFException {

    }

}