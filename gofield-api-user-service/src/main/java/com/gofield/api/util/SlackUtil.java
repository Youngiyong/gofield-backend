package com.gofield.api.util;

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
        field.put("text", String.format("*%s*\n%s", section, data));
        list.add(field);
        result.put("type", "section");
        result.put("fields", list);
        return result;
    }

    public static JSONObject makeImageSection(String section, String data, String imageUrl){
        JSONObject result = new JSONObject();
        JSONObject field = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        field.put("type", "mrkdwn");
        field.put("text", String.format("*%s*\n%s", section, data));
        list.add(field);
        result.put("type", "section");
        result.put("fields", list);
        Map<String, Object> accessory = new HashMap<>();
        accessory.put("type", "image");
        accessory.put("image_url", imageUrl!=null ? imageUrl : "https://pbs.twimg.com/profile_images/625633822235693056/lNGUneLX_400x400.jpg");
        accessory.put("alt_text", "cute cat");
        result.put("accessory", accessory);
        return result;
    }

    public static JSONObject makeItemImageSection(String itemName, String optionName, String thumbnail){
        JSONObject result = new JSONObject();
        JSONObject field = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        field.put("type", "mrkdwn");
        if(optionName==null){
            field.put("text", String.format("*?????????:*\n%s", itemName));
        } else {
            field.put("text", String.format("*?????????*\n%s\n%s)", itemName, optionName));
        }
        list.add(field);
        result.put("type", "section");
        result.put("fields", list);
        Map<String, Object> accessory = new HashMap<>();
        accessory.put("type", "image");
        accessory.put("image_url", thumbnail);
        accessory.put("alt_text", "item thumbnail");
        result.put("accessory", accessory);
        return result;
    }

    public static JSONObject makeError(String serverName, String url, String exception, String date, String message, String imageUrl) {
        List<JSONObject> blocks = new ArrayList<>();
        JSONObject header = makeHeader(serverName);
        JSONObject typeSection = makeSection("exception", exception);
        JSONObject urlSection = makeSection("url", url);
        JSONObject whenSection = makeSection("when", date);
        JSONObject messageSection = makeImageSection("message", message, imageUrl);
        blocks.add(header);
        blocks.add(typeSection);
        blocks.add(urlSection);
        blocks.add(whenSection);
        blocks.add(messageSection);
        JSONObject result = new JSONObject();
        result.put("blocks", blocks);
        return result;
    }

    public static JSONObject makeCancel(String username, String userTel, String orderNumber, String orderDate, String comment, String itemName, String optionName, String thumbnail, int totalAmount) {
        List<JSONObject> blocks = new ArrayList<>();
        JSONObject header = makeHeader(String.format("????????????: %s", orderNumber));
        JSONObject usernameSection = makeSection("??????", username);
        JSONObject userTelSection = makeSection("????????????", userTel);
        JSONObject orderDateSection = makeSection("????????????", orderDate);
        JSONObject totalAmountSection = makeSection("??????", String.valueOf(totalAmount));
        JSONObject commentSection = makeSection("??????", comment);
        JSONObject itemInfoSection = makeItemImageSection(itemName, optionName, thumbnail);
        blocks.add(header);
        blocks.add(usernameSection);
        blocks.add(userTelSection);
        blocks.add(itemInfoSection);
        blocks.add(totalAmountSection);
        blocks.add(commentSection);
        blocks.add(orderDateSection);
        JSONObject result = new JSONObject();
        result.put("blocks", blocks);
        return result;
    }

    public static JSONObject makeOrder(String username, String userTel, String orderNumber, String orderDate, String orderName,  int totalAmount) {
        List<JSONObject> blocks = new ArrayList<>();
        JSONObject header = makeHeader(String.format("????????????: %s", orderNumber));
        JSONObject usernameSection = makeSection("??????", username);
        JSONObject userTelSection = makeSection("????????????", userTel);
        JSONObject orderDateSection = makeSection("????????????", orderDate);
        JSONObject totalAmountSection = makeSection("??????", String.valueOf(totalAmount));
        JSONObject orderNameSection = makeSection("?????????", orderName);
        blocks.add(header);
        blocks.add(usernameSection);
        blocks.add(userTelSection);
        blocks.add(totalAmountSection);
        blocks.add(orderNameSection);
        blocks.add(orderDateSection);
        JSONObject result = new JSONObject();
        result.put("blocks", blocks);
        return result;
    }


    public static JSONObject makeQna(String username, String itemNumber, String itemName, String thumbnail, String title, String createDate) {
        List<JSONObject> blocks = new ArrayList<>();
        JSONObject header = makeHeader(String.format("????????????: %s", itemNumber));
        JSONObject usernameSection = makeSection("??????", username);
        JSONObject titleSection = makeSection("??????", title);
        JSONObject createDateSection = makeSection("????????????", createDate);
        JSONObject itemInfoSection = makeItemImageSection(itemName, null, thumbnail);
        blocks.add(header);
        blocks.add(usernameSection);
        blocks.add(titleSection);
        blocks.add(itemInfoSection);
        blocks.add(createDateSection);
        JSONObject result = new JSONObject();
        result.put("blocks", blocks);
        return result;
    }

}
