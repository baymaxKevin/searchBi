package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string,string->string,string,string,bigint,string,string,bigint,string,string"})
public class getArrayElement extends UDTF {

    @Override
    public void setup(ExecutionContext ctx) throws UDFException {

    }

    @Override
    public void process(Object[] args) throws UDFException {
        String url_parm = (String) args[0];
        String access_time = (String) args[1];
        String[] parms = url_parm.split("\\&");
        String keyword = null;
        String uid = null;
        long position = 0;
        String path = null;
        long app_id = 0;
        String os = null;
        String iid = null;
        String action = null;

        for(String parm : parms){
            if(parm.contains("keyword")){
                String[] keywords = parm.split("=");
                if(keywords.length > 1){
                    keyword = keywords[1];
                }
            }else if(parm.contains("myuid")||parm.contains("userid")){
                String[] uids = parm.split("=");
                if(uids.length > 1){
                    uid = uids[1];
                }
            }else if(parm.contains("path")){
                String[] paths = parm.split("=");
                if(paths.length > 1){
                    path = paths[1];
                }
                String[] ps = path.split("\\|");
                for(String p : ps){
                    if(p.length()==9 && p.substring(0,3).equals("026")){
                        try{
                            position = Long.parseLong(p.substring(6));
                        }catch (Exception e){
                            position = 0;
                        }
                    }
                }
            }else if(parm.contains("app_id")){
                if(parm.substring(0,6).equals("app_id")){
                    String[] app_ids = parm.split("=");
                    if(app_ids.length > 1){
                        try{
                            app_id = Long.parseLong(app_ids[1]);
                        }catch (Exception e){
                            app_id = 0;
                        }
                    }
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
            }else if(parm.contains("action")){
                String[] actions = parm.split("=");
                if(actions.length > 1){
                    action = actions[1];
                }
            }
        }
        forward(access_time,keyword,uid,position,iid,path,app_id,os,action);
    }

    @Override
    public void close() throws UDFException {

    }

}