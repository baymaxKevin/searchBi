package com.meiyou.innovative;

import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDFException;
import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.annotation.Resolve;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// TODO define input and output types, e.g. "string,string->string,bigint".
@Resolve({"string,string,string,string->string,bigint,string,string,string,string,bigint,string,bigint,bigint,string," +
        "string,string,string,string,string,string,string,string,string,string,string,string,string,string,string," +
        "string,string,string,string,bigint"})
public class GetBaseECBehavior extends UDTF {

    @Override
    public void setup(ExecutionContext ctx) throws UDFException {

    }

    @Override
    public void process(Object[] args) throws UDFException {
        // access_time,url_parm,request_body,method
        String access_time = (String) args[0];
        String url_parm = (String) args[1];
        String request_body = (String) args[2];
        String method = (String) args[3];
        String[] parms = url_parm.split("\\&");
        long channel_id = 0;
        long channel1_id = 0;
        long channel2_id = 0;
        String action = null;
        String platform = null;
        String request_uri = null;
        String title = null;
        long app_id = 0;
        String authorization = null;
        long brand_area_id = 0;
        long uid = 0;
        String ua = null;
        String os_version = null;
        String ot = null;
        String mac = null;
        String imei = null;
        String apn = null;
        String idfa = null;
        String adzone_id = null;
        String version = null;
        String path = null;
        String ip = null;
        String device_id = null;
        String origin = null;
        String bundle_id = null;
        String tbuid = null;
        String keyword = null;
        String os = null;
        String item_id = null;
        String pid = null;
        long exposure_time = 0;
        for (String parm : parms) {
            if (parm.contains("channelid")) {
                String[] channel1_ids = parm.split("=");
                if (channel1_ids.length > 1) {
                    String ch_id1 = channel1_ids[1];
                    try {
                        channel1_id = Long.parseLong(ch_id1);
                    } catch (Exception e) {
                        channel1_id = 0;
                    }
                }
            }if (parm.contains("channel_id")) {
                String[] channel2_ids = parm.split("=");
                if (channel2_ids.length > 1) {
                    String ch_id2 = channel2_ids[1];
                    try {
                        channel2_id = Long.parseLong(ch_id2);
                    } catch (Exception e) {
                        channel2_id = 0;
                    }
                }
            } else if (parm.contains("action")) {
                String[] actions = parm.split("=");
                if (actions.length > 1) {
                    action = actions[1];
                }
            } else if (parm.contains("platform")) {
                String[] platforms = parm.split("=");
                if (platforms.length > 1) {
                    platform = platforms[1];
                }
            } else if (parm.contains("request_uri")) {
                String[] request_uris = parm.split("=");
                if (request_uris.length > 1) {
                    request_uri = request_uris[1];
                }
            } else if (parm.contains("title")) {
                String[] titles = parm.split("=");
                if (titles.length > 1) {
                    title = titles[1];
                }
            } else if (parm.length() >= 6 && parm.substring(0, 6).equals("app_id")) {
                String[] app_ids = parm.split("=");
                if (app_ids.length > 1) {
                    String appId = app_ids[1];
                    try {
                        app_id = Long.parseLong(appId);
                    } catch (Exception e) {
                        app_id = 0;
                    }
                }
            } else if (parm.contains("authorization")) {
                String[] authorizations = parm.split("=");
                if (authorizations.length > 1) {
                    authorization = authorizations[1];
                }
            } else if (parm.contains("brand_area_id")) {
                String[] brand_area_ids = parm.split("=");
                if (brand_area_ids.length > 1) {
                    String bid = brand_area_ids[1];
                    try {
                        brand_area_id = Long.parseLong(bid);
                    } catch (Exception e) {
                        brand_area_id = 0;
                    }
                }
            } else if (parm.contains("myuid") || parm.contains("userid")) {
                String[] uids = parm.split("=");
                if (uids.length > 1) {
                    String myuid = uids[1];
                    try {
                        uid = Long.parseLong(myuid);
                    } catch (Exception e) {
                        uid = 0;
                    }
                }
            } else if (parm.contains("statinfo")) {
                String[] statinfos = parm.split("=");
                String statinfo = null;
                JsonObject json = null;
                if (statinfos.length > 1) {
                    try {
                        statinfo = new String(java.util.Base64.getDecoder().decode(statinfos[1]), "utf-8");
                        json = (JsonObject)new JsonParser().parse(statinfo);
                    } catch (Exception e) {
                        statinfo = statinfo;
                        json = json;
                    }

                    try{
                        ua = json.get("ua").getAsString();
                    }catch (Exception e){
                        ua = null;
                    }
                    try{
                        os_version = json.get("os_version").getAsString();
                    }catch (Exception e){
                        os_version = null;
                    }
                    try{
                        ot = json.get("ot").getAsString();
                    }catch (Exception e){
                        ot = null;
                    }
                    try{
                        mac = json.get("mac").getAsString();
                    }catch (Exception e){
                        mac = null;
                    }
                    try{
                        imei = json.get("imei").getAsString();
                    }catch (Exception e){
                        imei = null;
                    }
                    try{
                        apn = json.get("apn").getAsString();
                    }catch (Exception e){
                        apn = null;
                    }
                    try{
                        idfa = json.get("idfa").getAsString();
                    }catch (Exception e){
                        idfa = null;
                    }
                    try {
                        os = json.get("os").getAsString();
                    } catch (Exception e) {
                        os = null;
                    }
                }
            }else if (parm.contains("adzone_id")) {
                String[] adzone_ids = parm.split("=");
                if (adzone_ids.length > 1) {
                    adzone_id = adzone_ids[1];
                }
            } else if(parm.contains("v=")){
                String[] vs = parm.split("=");
                if(vs.length > 1){
                    version = vs[1];
                }
            } else if (parm.contains("path")) {
                String[] paths = parm.split("=");
                if (paths.length > 1) {
                    path = paths[1];
                }
            }else if (parm.contains("ip")) {
                String[] ips = parm.split("=");
                if (ips.length > 1) {
                    ip = ips[1];
                }
            } else if (parm.contains("device_id")) {
                String[] device_ids = parm.split("=");
                if (device_ids.length > 1) {
                    device_id = device_ids[1];
                }
            } else if (parm.contains("origin")) {
                String[] origins = parm.split("=");
                if (origins.length > 1) {
                    origin = origins[1];
                }
            } else if (parm.contains("bundleid")) {
                String[] bundle_ids = parm.split("=");
                if (bundle_ids.length > 1) {
                    bundle_id = bundle_ids[1];
                }
            } else if (parm.contains("tbuid")) {
                String[] tbuids = parm.split("=");
                if (tbuids.length > 1) {
                    tbuid = tbuids[1];
                }
            } else if (parm.contains("keyword")) {
                String[] keywords = parm.split("=");
                if (keywords.length > 1) {
                    keyword = keywords[1];
                }
            } else if (parm.contains("item_id")) {
                String[] item_ids = parm.split("=");
                if (item_ids.length > 1) {
                    item_id = item_ids[1];
                }
            } else if (parm.contains("pid")) {
                String[] pids = parm.split("=");
                if (pids.length > 1) {
                    pid = pids[1];
                }
            }
        }
        if(channel1_id!=channel2_id){
            channel_id = Math.max(channel1_id,channel2_id);
        }
        try{
            JsonObject json = (JsonObject)new JsonParser().parse(request_body);
            JsonArray exposure_list = json.get("exposure_list").getAsJsonArray();
//            String exposure_list = JSON.parseObject(request_body).getString("exposure_list");
//            JSONArray brands_json = JSON.parseArray(exposure_list);
            for(int i = 0; i < exposure_list.size(); i++){
                long ba_id = 0;
                String i_id = null;
                exposure_time = 0;
                JsonObject js = (JsonObject)exposure_list.get(i);
                if(js.toString().contains("item_id")){
                    long bid = 0;
                    String iid = null;
                    long et = 0;
                    try{
                        JsonObject appendParams = js.get("appendParams").getAsJsonObject();
                        bid = Long.parseLong(appendParams.get("brand_area_id").getAsString());
                        ba_id = bid;
                    }catch (Exception e) {
                        try{
                            bid = Long.parseLong(js.get("brand_area_id").getAsString());
                            ba_id = bid;
                        }catch (Exception e1){
                            ba_id = brand_area_id;
                        }
                    }
                    try{
                        iid = js.get("item_id").getAsString();
                        i_id = iid;
                    }catch (Exception e){
                        i_id = item_id;
                    }
                    try{
                        et = Long.parseLong(js.get("times").getAsString());
                        exposure_time = et;
                    }catch (Exception e){
                        exposure_time = et;
                    }
                }else{
                    long bid = 0;
                    String iid = null;
                    long et = 0;
                    try{
                        bid = Long.parseLong(js.get("brand_area_id").getAsString());
                        ba_id = bid;
                    }catch (Exception e){
                        ba_id = brand_area_id;
                    }
                    try{
                        iid = js.get("id").getAsString();
                        i_id = iid;
                    }catch (Exception e){
                        i_id = null;
                    }
                    try{
                        et = Long.parseLong(js.get("times").getAsString());
                        exposure_time = et;
                    }catch (Exception e){
                        exposure_time = et;
                    }
                }
                forward(method,channel_id,action,platform,request_uri,title,app_id,authorization,ba_id,uid,
                        ua,os_version,ot,mac,imei,apn,idfa,adzone_id,version,path,ip,device_id,origin,bundle_id,
                        tbuid,access_time,keyword,os,i_id,pid,exposure_time);
            }
        }catch (Exception e){
            forward(method,channel_id,action,platform,request_uri,title,app_id,authorization,brand_area_id,uid,
                    ua,os_version,ot,mac,imei,apn,idfa,adzone_id,version,path,ip,device_id,origin,bundle_id,tbuid,
                    access_time,keyword,os,item_id,pid,exposure_time);
        }
    }

    @Override
    public void close() throws UDFException {

    }
}