package car.com.wlc.cardemo.javaBean;

/**
 * Created by Administrator on 2017/3/9.
 * 轮胎
 */

public class Tire extends Product {
    public Tire(int im, String name, String info,String brand,int inventory,int sale,String price,String oprice) {
        this.image = im;
        this.name = name;
        this.info = info;
        this.brand=brand;
        this.inventory=inventory;
        this.sale=sale;
        this.price=price;
        this.oprice= oprice;
    }

}
