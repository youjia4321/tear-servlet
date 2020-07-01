package com.wang.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerParser {

    public static int getServerPort() {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read("WebServer/conf/server.xml");

            Element element = (Element) document.selectSingleNode("//connector");
            try {
                return Integer.parseInt(element.attribute("port").getValue());
            } catch (Exception e) {
                return 8080;
            }

        } catch (DocumentException ignored) {
        }

        return 8080;
    }

}
