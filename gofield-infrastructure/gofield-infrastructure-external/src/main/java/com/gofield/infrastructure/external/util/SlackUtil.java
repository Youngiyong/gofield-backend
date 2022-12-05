package com.gofield.infrastructure.external.util;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlackUtil {

    public static JSONObject makeHeader(String serverName){
        JSONObject header = new JSONObject();
        JSONObject text = new JSONObject();
        text.put("type", "plain_text");
        text.put("text", serverName);
        text.put("emoji", true);
        header.put("type", "header");
        header.put("text", text);
        return header;
    }
    public static JSONObject makeSection(String section, String data){
        JSONObject result = new JSONObject();
        JSONObject field = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        field.put("type", "mrkdwn");
        field.put("text", String.format("*%s:*\n%s", section, data));
        list.add(field);
        result.put("type", "section");
        result.put("fields", list);
        return result;
    }

    public static JSONObject makeImageSection(String section, String data, String url){
        JSONObject result = new JSONObject();
        JSONObject field = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        field.put("type", "mrkdwn");
        field.put("text", String.format("*%s:*\n%s", section, data));
        list.add(field);
        result.put("type", "section");
        result.put("fields", list);
        Map<String, Object> accessory = new HashMap<>();
        accessory.put("type", "image");
        accessory.put("image_url", url!=null ? url : "https://pbs.twimg.com/profile_images/625633822235693056/lNGUneLX_400x400.jpg");
        accessory.put("alt_text", "cute cat");
        result.put("accessory", accessory);

        return result;
    }

    public static JSONObject makeError(String serverName, String url, String exception, String date, String message) {

        List<JSONObject> blocks = new ArrayList<>();
        JSONObject header = makeHeader(serverName);
        JSONObject typeSection = makeSection("exception", exception);
        JSONObject urlSection = makeSection("url", url);
        JSONObject whenSection = makeSection("when", date);
        JSONObject messageSection = makeImageSection("message", message, null);
        blocks.add(header);
        blocks.add(typeSection);
        blocks.add(urlSection);
        blocks.add(whenSection);
        blocks.add(messageSection);
        JSONObject result = new JSONObject();
        result.put("blocks", blocks);
        return result;
    }

}
