package car.com.wlc.cardemo.javaBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlx on 2016/12/11.
 */

public class CityBean implements Serializable {
    static String cityJson = "{'cities':['上海市','北京市','杭州市','广州市','南京市','苏州市','深圳市','成都市','重庆市','天津市','宁波市','扬州市','无锡市','福州市','厦门','武汉','西安','沈阳','大连','青岛','济南','海口','石家庄','唐山','秦皇岛','邯郸','邢台','保定市','张家口','承德','沧州','廊坊','衡水','太原','大同','阳泉','长治','晋城','朔州','晋中','运城','忻州','临汾','吕梁','呼和浩特','包头','乌海','赤峰','通辽','鄂尔多斯','呼伦贝尔','兴安盟','锡林郭勒','乌兰察布','巴彦淖尔','阿拉善','鞍山','抚顺','本溪','丹东','锦州','营口','阜新','辽阳','盘锦','铁岭','朝阳','葫芦岛','长春','吉林','四平','辽源','通化','白山','松原','白城','延边','哈尔滨','齐齐哈尔','鸡西','鹤岗','双鸭山','大庆','伊春','佳木斯','七台河','牡丹江','黑河','绥化','大兴安岭','徐州','常州','南通','连云港','淮安','盐城','镇江','泰州','宿迁','温州','嘉兴','湖州','绍兴','金华','衢州','舟山','台州','丽水','合肥','芜湖','蚌埠','淮南','马鞍山','淮北','铜陵','安庆','黄山','滁州','阜阳','宿州','六安','亳州','池州','宣城','莆田','三明','泉州','漳州','南平','龙岩','宁德','南昌','景德镇','萍乡','九江','新余','鹰潭','赣州','吉安','宜春','抚州','上饶','淄博','枣庄','东营','烟台','潍坊','济宁','泰安','威海','日照','莱芜','临沂','德州','聊城','滨州','菏泽','郑州','开封','洛阳','平顶山','安阳','鹤壁','新乡','焦作','濮阳','许昌','漯河','三门峡','南阳','商丘','信阳','周口','驻马店','黄石','十堰','宜昌','襄阳','鄂州','荆门','孝感','荆州','黄冈','咸宁','随州','恩施州','仙桃','潜江','天门','株洲','湘潭','衡阳','邵阳','岳阳','常德','张家界','益阳','郴州','永州','怀化','娄底','湘西','韶关','珠海','汕头','佛山','江门','湛江','茂名','肇庆','惠州','梅州','汕尾','河源','阳江','清远','东莞','中山','潮州','揭阳','云浮','南宁','柳州','桂林','梧州','北海','防城港','钦州','贵港','玉林','百色','贺州','河池','自贡','攀枝花','泸州','德阳','绵阳','广元','遂宁','内江','乐山','南充','眉山','宜宾','广安','达州','雅安','巴中','资阳','阿坝','甘孜州','凉山','贵阳','六盘水','遵义','安顺','铜仁地区','黔西南','毕节地区','黔东南','黔南','昆明','曲靖','玉溪','保山','昭通','楚雄州','红河','文山州','普洱','西双版纳','大理州','德宏','丽江','怒江','迪庆','临沧','拉萨','昌都地区','山南','日喀则地区','那曲','阿里','林芝地区','铜川','宝鸡','咸阳','渭南','延安','汉中','榆林','安康','商洛','兰州','嘉峪关','金昌','白银','天水','武威','张掖','平凉','酒泉','庆阳','定西','陇南','临夏州','甘南','西宁','海东','海北','黄南','果洛','玉树','海西','银川','石嘴山','吴忠','固原','乌鲁木齐','克拉玛依','吐鲁番地区','哈密地区','昌吉州','博尔塔拉','巴音郭楞','阿克苏地区','克孜勒苏','喀什地区','和田地区','伊犁','塔城地区','阿勒泰地区','石河子','香港','澳门','长沙','三亚','中卫','儋州','保亭','昌江','澄迈县','崇左','定安县','东方','济源','来宾','乐东','陵水','琼海','神农架林区','图木舒克','屯昌县','万宁','文昌','海南州']}";

    public static List<String> getSampleContactList()
    {
        List<String> list = new ArrayList<>();

        try
        {
            JSONObject jo1 = new JSONObject(cityJson);

            JSONArray ja1 = jo1.getJSONArray("cities");

            for(int i = 0; i < ja1.length(); i++)
            {
                String cityName = ja1.getString(i);

                list.add(cityName);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return list;
    }


    private String name;
    private String BrandId ;
    private String productId;
    private String ProductName;
    private String styleName;
    private String displacement;
    private int transmissionType;
    private String logoPath;

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public int getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(int transmissionType) {
        this.transmissionType = transmissionType;

    }

    @Override
    public String toString() {
        return "CityBean{" +
                "name='" + name + '\'' +
                ", BrandId='" + BrandId + '\'' +
                ", productId='" + productId + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", styleName='" + styleName + '\'' +
                ", displacement='" + displacement + '\'' +
                ", transmissionType=" + transmissionType +
                '}';
    }
}
