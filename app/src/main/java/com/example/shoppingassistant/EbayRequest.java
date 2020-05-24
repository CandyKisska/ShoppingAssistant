package com.example.shoppingassistant;

import android.util.Log;

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
        String timeLeft = "";
        String price = "";
        String image = "";
        String name = "";
        String bidCount = "";
        String priceFormat = "";
        String oldPrice = "";
        int quantityLeft = 0;
        boolean chinese = false;
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("Item");
            NodeList nodeList = doc.getElementsByTagName("CurrentPrice");
            priceFormat = nodeList.item(0).getAttributes().getNamedItem("currencyID").getNodeValue();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    timeLeft = getValue("TimeLeft", element);

                    price = getValue("CurrentPrice", element);
                    image = getValue("PictureURL", element);
                    name = getValue("Title", element);
                    if (getValue("ListingType", element).equals("Chinese")) {
                        chinese = true;
                        bidCount = getValue("BidCount", element);
                    }
                    quantityLeft = Integer.parseInt(getValue("Quantity", element)) - Integer.parseInt(getValue("QuantitySold", element));
                    oldPrice = getValue("OriginalRetailPrice", element);
                }
            }
        } catch (Exception e) {
            Log.println(Log.ASSERT, "fuck", "fuck");
        }
        return new Product(timeLeft, image, name, price, priceFormat, oldPrice, quantityLeft, chinese, bidCount, url);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
