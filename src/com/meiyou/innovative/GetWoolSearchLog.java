package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string->string,bigint,string,string,bigint,string,string,string,string,bigint,bigint,string"})
public class GetWoolSearchLog extends UDTF {

    @Override
    public void setup(ExecutionContext ctx) throws UDFException {

    }

    @Override
    public void process(Object[] args) throws UDFException {
        // access_time,url_parm,request_body,method
        String log = (String) args[0];
        String keyword = "";
        long uid = 0;
        long position = 0;
        long app_id = 0;
        String version = "";
        String platform = "";
        String search_type = "";
        String adzone_id = "";
        String search_counts = "";
        long search_count = -1;
        String search_result = "";
        String page_index = "";
        String sort_type = "";
        JsonObject json = null;
        String info1 = "";
        String search_params = "";
        if(log.contains("INFO") && log.contains("wool search params")){
            try{
                info1 = log.split(" - ")[1];
            }catch (Exception e){
                info1 = "";
            }
            String[] info2 = info1.split("},");
            try{
                search_params = info2[0].concat("}").split("=",2)[1];
            }catch(Exception e){
                search_params = "";
            }
            try{
                for(String x:info2[1].split(",")){
                    if(x.contains("result count")){
                        search_counts = x;
                    }else if(x.contains("result from")){
                        if(x.contains("station out")){
                            search_type = "taobao";
                        }else if(x.contains("station in")){
                            search_type = "wool";
                        }
                    }
                }
                search_count = Long.parseLong(search_counts.split("=",2)[1]);
            }catch (Exception e){
                search_type = "";
                search_count = -1;
            }
            if(search_count == 0 ) {
                search_result = "SN";
            }else if(search_count > 0){
                search_result = "S";
            }
            try{
                json = (JsonObject)new JsonParser().parse(search_params);
            }catch (Exception e){
                json = null;
            }
            try{
              page_index = json.get("page_index").getAsString();
            }catch(Exception e){
                page_index = "";
            }
            try{
                sort_type = json.get("sort_type").getAsString();
            }catch(Exception e){
                sort_type = "";
            }
            try{
                if(search_params.contains("keyword")){
                    keyword = json.get("keyword").getAsString();
                }else if(search_params.contains("q")){
                    keyword = json.get("q").getAsString();
                }
            }catch (Exception e){
                keyword = "";
            }
            try{
                app_id = Long.parseLong(json.get("app_id").getAsString());
            }catch (Exception e){
                app_id = 0;
            }
            try{
                position = Long.parseLong(json.get("position").getAsString());
            }catch (Exception e){
                position = 0;
            }
            try{
                version = json.get("version").getAsString();
            }catch (Exception e){
                version = "";
            }
            try{
                uid = Long.parseLong(json.get("uid").getAsString());
            }catch(Exception e){
                uid = 0;
            }
            try{
                platform = json.get("platform").getAsString();
            }catch (Exception e){
                platform = "";
            }
            try{
                adzone_id = json.get("adzone_id").getAsString();
            }catch (Exception e){
                adzone_id = "";
            }

            forward(keyword,uid,search_type,platform,search_count,search_result,adzone_id,page_index,sort_type,
                    position,app_id,version);
        }else{
            forward(keyword,uid,search_type,platform,search_count,search_result,adzone_id,page_index,sort_type,
                    position,app_id,version);
        }
    }

    @Override
    public void close() throws UDFException {

    }
}