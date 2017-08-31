package com.hzu.jpg.commonwork.enity;

import java.util.List;

/**
 * Created by cimcitech on 2017/8/25.
 */

public class GoodsRecordingVo {
    /**
     * orderList : [{"account":"18666660749","goods_id":164,"id":7,"is_del":1,"link_phone":"10086","name":"ds","orderaddress":"","shopInfo":{"amount":"","brand":"","describes":"女神外套产品","id":164,"is_del":"1","name":"女神外套","picture":"/HRM/upload/image/shopmall/164.jpg","point":"100","price":"2000","produceArea":"","saleamount":"2","weight":""},"status":0,"user_id":379},{"account":"18666660749","goods_id":164,"id":6,"is_del":1,"link_phone":"10086","name":"ds","orderaddress":"","shopInfo":{"amount":"","brand":"","describes":"女神外套产品","id":164,"is_del":"1","name":"女神外套","picture":"/HRM/upload/image/shopmall/164.jpg","point":"100","price":"2000","produceArea":"","saleamount":"2","weight":""},"status":0,"user_id":379}]
     * status : 1
     */

    private String status;
    private List<OrderListBean> orderList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * account : 18666660749
         * goods_id : 164
         * id : 7
         * is_del : 1
         * link_phone : 10086
         * name : ds
         * orderaddress :
         * shopInfo : {"amount":"","brand":"","describes":"女神外套产品","id":164,"is_del":"1","name":"女神外套","picture":"/HRM/upload/image/shopmall/164.jpg","point":"100","price":"2000","produceArea":"","saleamount":"2","weight":""}
         * status : 0
         * user_id : 379
         */

        private String account;
        private int goods_id;
        private int id;
        private int is_del;
        private String link_phone;
        private String name;
        private String orderaddress;
        private ShopInfoBean shopInfo;
        private int status;
        private int user_id;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getLink_phone() {
            return link_phone;
        }

        public void setLink_phone(String link_phone) {
            this.link_phone = link_phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderaddress() {
            return orderaddress;
        }

        public void setOrderaddress(String orderaddress) {
            this.orderaddress = orderaddress;
        }

        public ShopInfoBean getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfoBean shopInfo) {
            this.shopInfo = shopInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public static class ShopInfoBean {
            /**
             * amount :
             * brand :
             * describes : 女神外套产品
             * id : 164
             * is_del : 1
             * name : 女神外套
             * picture : /HRM/upload/image/shopmall/164.jpg
             * point : 100
             * price : 2000
             * produceArea :
             * saleamount : 2
             * weight :
             */

            private String amount;
            private String brand;
            private String describes;
            private int id;
            private String is_del;
            private String name;
            private String picture;
            private String point;
            private String price;
            private String produceArea;
            private String saleamount;
            private String weight;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIs_del() {
                return is_del;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProduceArea() {
                return produceArea;
            }

            public void setProduceArea(String produceArea) {
                this.produceArea = produceArea;
            }

            public String getSaleamount() {
                return saleamount;
            }

            public void setSaleamount(String saleamount) {
                this.saleamount = saleamount;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }
        }
    }
}
