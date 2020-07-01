package com.wang.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebParser {

    // 解析web.xml文件

    public static Map<String, String> servletInfos = new HashMap<>();

    public static void parseWebXml(String appName) {

        try {

            Map<String, String> servlets = new HashMap<>();
            Map<String, String> servletMappings = new HashMap<>();

            SAXReader reader = new SAXReader();
            String xmlPath = "./WebServer/webapps" + File.separator + appName + File.separator + "WEB-INF/web.xml";
            Document document = reader.read(xmlPath);

            Element root = document.getRootElement();

            List<Element> elements = root.elements("servlet");
            for (Element servlet : elements) {
                servlets.put(servlet.elements().get(0).getText(), servlet.elements().get(1).getText());
            }


            List<Element> servletMaps = root.elements("servlet-mapping");
            for (Element servlet : servletMaps) {
                servletMappings.put(servlet.elements().get(0).getText(), servlet.elements().get(1).getText());
            }

            for (String key : servlets.keySet()) {
                servletInfos.put(servletMappings.get(key), servlets.get(key));
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        parseWebXml("OA");
    }

}
