package com.todos.api.utility;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonResponse {
    public static JSONObject createResponse(int status, String response){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            jsonObject.put("message", response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
