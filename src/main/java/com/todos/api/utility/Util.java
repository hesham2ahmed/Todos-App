package com.todos.api.utility;

import org.json.JSONObject;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.util.Iterator;

public abstract class Util {
    public static ServletRequest readDataFromRequest(ServletRequest request) {
        JSONObject jsonObject;
        BufferedReader reader;
        StringBuilder data = new StringBuilder();
        String line = null;
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null)
                data.append(line);
            if(data.length() != 0) {
                jsonObject = new JSONObject(data.toString());
                Iterator keys = jsonObject.keys();
                while(keys.hasNext()){
                    String key = keys.next().toString();
                    request.setAttribute(key, jsonObject.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
