package com.wang.core;

import com.javax.servlet.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestObject implements HttpServletRequest {

    private Map<String, List<String>> map = new HashMap<>();


    public RequestObject(String uri) {
        if(uri.contains("?")) {

            String[] uriAndParameters = uri.split("[?]");
            if(uriAndParameters[1].contains("&")) {
                String[] keyAndValues = uriAndParameters[1].split("&");
                for (String kv : keyAndValues) {
                    List<String> list = new ArrayList<>();
                    String[] keyAndValue = kv.split("=");
                    if(map.containsKey(keyAndValue[0])) {
                        map.get(keyAndValue[0]).add(keyAndValue[1]);
                    } else {
                        list.add(keyAndValue[1]);
                        map.put(keyAndValue[0], list);
                    }
                }

            } else {
                List<String> list = new ArrayList<>();
                String[] keyAndValue = uriAndParameters[1].split("=");
                list.add(keyAndValue[1]);
                map.put(keyAndValue[0], list);
            }

        }
    }

    @Override
    public String getParameter(String name) {
        try{
            List<String> values = map.get(name);
            if(values.size() == 1){
                return values.get(0);
            }
            return "";
        } catch (Exception e) {
            return "";
        }

    }

    @Override
    public String[] getParameterValues(String name) {
        try {
            List<String> values = map.get(name);
            if(values.size() > 0){
                String[] arr = new String[values.size()];
                for (int i = 0; i < values.size(); i++) {
                    arr[i] = values.get(i);
                }
                return arr;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
