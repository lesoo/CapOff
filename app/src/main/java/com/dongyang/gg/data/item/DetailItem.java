package com.dongyang.gg.data.item;

public class DetailItem {


        String pic;
        String name;
        String price;
        String intro;
        String num;

        public DetailItem(String pic, String name, String price, String num, String intro){
            this.pic=pic;
            this.name=name;
            this.price=price;
            this.num=num;
            this.intro=intro;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }



        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    public String getNum() {
        return num;
    }

    public void setNum(String pic) {
        this.pic = num;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
    }

