package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string->string,bigint,bigint,string,string,string,string"})
public class GetSearchLog extends UDTF {

    @Override
    public void setup(ExecutionContext ctx) throws UDFException {

    }

    @Override
    public void process(Object[] args) throws UDFException {
        // access_time,url_parm,request_body,method
        String log = (String) args[0];
        String keyword = "";
        long uid = 0;
        long app_id = 0;
        String post_type = "";
        String pid_type = "";
        String platform = "";
        String search_result = "";
        JsonObject json = null;
        String info1 = "";
        String search_params = "";
        String repo_data = "";
        if(log.contains("search params")&&log.contains("repo_data")){
            try{
                info1 = log.split(" - ")[1];
            }catch (Exception e){
                info1 = "";
            }
            String[] info2 = info1.split(", ");
            try{
                search_params = info2[0].split("=",2)[1];
            }catch(Exception e){
                search_params = "";
            }
            try{
                repo_data = info2[1].split("=",2)[1];
            }catch (Exception e){
                repo_data = "";
            }
            try{
                json = (JsonObject)new JsonParser().parse(search_params);
            }catch (Exception e){
                json = null;
            }
            try{
                keyword = json.get("keyword").getAsString();
            }catch (Exception e){
                keyword = "";
            }
            try{
                uid = Long.parseLong(json.get("uid").getAsString());
            }catch(Exception e){
                uid = 0;
            }
            try{
                app_id = Long.parseLong(json.get("app_id").getAsString());
            }catch (Exception e){
                app_id = 0;
            }
            try{
                post_type = json.get("post_type").getAsString();
            }catch (Exception e){
                post_type = "";
            }
            try{
                pid_type = json.get("pid_type").getAsString();
            }catch (Exception e){
                pid_type = "";
            }
            try{
                platform = json.get("platform").getAsString();
            }catch (Exception e){
                platform = "";
            }
            if(repo_data.equals("[]")){
                search_result = "SN";
            }else{
                search_result = "S";
            }
            forward(keyword,uid,app_id,post_type,pid_type,platform,search_result);
        }else{
            forward(keyword,uid,app_id,post_type,pid_type,platform,search_result);
        }
    }

    @Override
    public void close() throws UDFException {

    }
}