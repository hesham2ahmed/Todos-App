package com.todos.api.utility;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonResponse {
    public static JSONObject createResponse(int status, String message,JSONObject data){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            jsonObject.put("message", message);
            jsonObject.put("value", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
