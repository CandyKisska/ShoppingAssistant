package com.example.shoppingassistant;

public class Data {

        public String offer_id;
        public int product_id;
        public int sku;

        public Data(String offer_id, int product_id, int sku) {
            this.offer_id = offer_id;
            this.product_id = product_id;
            this.sku = sku;
        }

    public Data(int sku) {
        this.sku = sku;
    }
}
