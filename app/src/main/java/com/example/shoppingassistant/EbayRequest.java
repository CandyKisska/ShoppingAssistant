package com.example.shoppingassistant;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EbayRequest {
    static OkHttpClient client = new OkHttpClient();
    String url = "https://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=XML&appid=C-Shopping-PRD-bc51f635b-dd94064c&siteid=77&version=967&ItemID=283565435384&IncludeSelector=Details";

    public EbayRequest() {
    }

    public String get() throws IOException {
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String price(String xml) {
         String p="";
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("Item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                     p = getValue("CurrentPrice", element);

                }
            }
        } catch (Exception ex) {
            return ex.toString();
        }
        return p;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
