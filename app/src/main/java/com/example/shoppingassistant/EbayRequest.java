package com.example.shoppingassistant;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.OkHttpClient;


public class EbayRequest {
    static OkHttpClient client = new OkHttpClient();

    public EbayRequest() {
    }

    public Product get(String url) {
        String price = "";
        String image = "";
        String name = "";
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("Item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    price = getValue("CurrentPrice", element);
                    image = getValue("PictureURL", element);
                    name = getValue("Title", element);


                }
            }
        } catch (Exception ex) {
            System.out.println("FUCK");
        }
        return new Product(image,name,price);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
