package car.com.wlc.cardemo.javaBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class Product implements Serializable{
    public int image;
    public String name;
    public String info;
    public String brand;
    public int inventory;
    public int sale;
    public String price;
    public String oprice;

    @Override
    public String toString() {
        return "Product{" +
                "image=" + image +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", brand='" + brand + '\'' +
                ", inventory=" + inventory +
                ", sale=" + sale +
                ", price='" + price + '\'' +
                ", oprice='" + oprice + '\'' +
                '}';
    }
}
