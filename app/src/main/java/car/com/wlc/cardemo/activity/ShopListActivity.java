package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.ShopListAdapter;
import car.com.wlc.cardemo.javaBean.EngineOil;
import car.com.wlc.cardemo.javaBean.Product;
import car.com.wlc.cardemo.javaBean.Repair;
import car.com.wlc.cardemo.javaBean.Tire;


public class ShopListActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Product> list;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Intent intent =  getIntent();
        type = intent.getIntExtra("type", 0);


        initView();
    }

    private void initView() {
        findViewById(R.id.classify_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        gridView = ((GridView) findViewById(R.id.shop_list_grid));
        if (type==0){
            //轮胎
            Tire tire1 = new Tire(R.mipmap.luntai, "风神轮胎001", "风神轮胎股份有限公司是国家520户大型重点企业、全国大型轮胎制造企业、轮胎出口基地、中国十大品牌轮胎，连续多年位列世界轮胎行业75强前25名。主要生产“河南”牌、“风神”牌工程机械轮胎、全钢载重子午线轮胎、斜交载重汽车轮胎、特种轮胎、轻卡轮胎、农用轮胎、军用轮胎等7大系列400多个规格品种。拥有博士后科研工作站和国家级企业技术中心。“风神”商标是中国驰名商标，“风神”牌全钢载重子午胎是中国名牌产品。", "风神轮胎",48,2,"699.00","899.00");
            Tire tire2 = new Tire(R.mipmap.luntai, "奔驰 G级(进口) 5.0L 自动 G500 标准型", "奔驰，德国汽车品牌，被认为是世界上最成功的高档汽车品牌之一，其完美的技术水平、过硬的质量标准、推陈出新的创新能力、以及一系列经典轿跑车款式令人称道。", "米其林轮胎",49,1," 2999.00","5999.00");
            Tire tire3 = new Tire(R.mipmap.luntai, "米其林轮胎001", "米其林公司创建于1889年的法国克莱蒙费朗。在100多年的时间中，米其林公司经历了持续不断的创新和发展。现拥有世界五大洲的业务运营以及位于欧洲、北美和亚洲的研发中心，并在全球超过170个国家中进行产品营销。米其林公司全球共有113529位员工、69家制造工厂和2个橡胶种植园。年产1.9亿条轮胎、1700万册地图和指南。", "米其林轮胎",50,0,"1099.00","1299.00");
            Tire tire4 = new Tire(R.mipmap.luntai, "保时捷 帕纳梅拉 3.0 THYBRID-自动 Pana 4", "保时捷股份公司是全球最大、历史最悠久的跑车制造商，也是最具盈利能力的汽车制造商。", "普利司通",50,0,"2300.00","2530.00");
            Tire tire5 = new Tire(R.mipmap.luntai, "普利司通001", "日本普利司通(BRIDGESTONE)公司是世界最大的轮胎及橡胶产品生产商，也是世界轮胎业三巨头之一。雄居世界橡胶业及轮胎业前列的普利司通集团，自1931年创建以来，始终奉行“以最高品质贡献社会”的企业方针，坚持为具有不同消费需求的用户提供所喜爱的商品。以领先于时代的尖端技术和积极果敢的进取精神，从开发、生产、销售、物流到售后服务均实行全方位的品质管理。普利司通作为享誉世界的跨国公司，目前销售区域遍布全球150多个国家，是世界上最大的轮胎制造厂商之一，并稳居财富杂志世界500强排名之列。", "普利司通",50,0,"599.00","499.00");
            Tire tire6 = new Tire(R.mipmap.luntai, "韩泰轮胎", "韩泰轮胎成立于1941年，是韩国的第一家轮胎企业。韩泰为乘用车、轻卡（SUV,RV等）、货车、巴士和专用赛车提供子午轮胎。韩泰在全球有五家研发中心，确保韩泰轮胎能够满足每个区域消费者的需求和提升消费者满意度。韩泰轮胎的产品遍及全球180个国家。它在全球的员工总数超过20,000名。韩泰轮胎于1996年进入中国。", "韩泰轮胎",35,3,"399.00","499.00");
            Tire tire7 = new Tire(R.mipmap.luntai, "横滨001", "日本横滨橡胶株式会社制造，成立于1917年总部设在日本横滨。该产品，1981年至今一直被澳门格兰披治大赛会指定为赛事轮胎，日本所有汽车制造商以及保时捷等，均使用横滨轮胎为标准轮胎。该产品率先获得我国商检部门CCIB认证。", "横滨轮胎",50,0,"300.00","400.00");
            Tire tire8 = new Tire(R.mipmap.luntai, "德国马牌001", "来自欧洲的汽车轮胎品牌德国马牌(Continental),产品包括越野车胎、轿车胎,为您提供冬季胎夏季胎各种轮胎保养知识,即刻与德国马牌一起体验非凡轮动!", "德国马牌",50,0,"500.00","600.00");

            list=new ArrayList<>();
            list.add(tire1);
            list.add(tire2);
            list.add(tire3);
            list.add(tire4);
            list.add(tire5);
            list.add(tire6);
            list.add(tire7);
            list.add(tire8);

            //补胎
            Repair repair1 = new Repair(R.mipmap.lingbu, "冷补", "冷补主要是通过冷补胶片和胶水来覆盖住原来气密层上的孔，实现修补的目的。", "补胎", 50, 0, "20.00", "30.00");
            Repair repair2 = new Repair(R.mipmap.rebu, "热补", "热补在很长一段时间里被认为是最好的补胎方式。", "补胎", 50, 0, "30.00", "40.00");
            Repair repair3 = new Repair(R.mipmap.moguding, "蘑菇钉补胎", "蘑菇钉的冠部起到和冷补胶片一样，能对轮胎气密层进行密封；蘑菇钉的柄则可以穿过钉孔对胎体橡胶进行修复。", "补胎", 50, 0, "60.00", "80.00");
            list.add(repair1);
            list.add(repair2);
            list.add(repair3);

            //机油
            EngineOil en = new EngineOil(R.mipmap.jiyou, " 嘉实多机油", "嘉实多是世界公认的润滑油专家。BP（英国石油公司）旗下的一个子品牌。嘉实多的成功归功于Charles Cheers Wakefield，是他在1899年建立了专业的润滑油公司——嘉实多，并确立以技术先锋、获胜、热诚与激情、卓越表现为公司的核心价值，这些价值仍能代表嘉实多，而且是使嘉实多成功的核心力量。", "机油", 50, 0, "100.00", "150.00");
            list.add(en);
            gridView.setAdapter(new ShopListAdapter(this,list,type));

        }else if (type==1){
            Tire tire1 = new Tire(R.mipmap.luntai, "风神轮胎001", "风神轮胎股份有限公司是国家520户大型重点企业、全国大型轮胎制造企业、轮胎出口基地、中国十大品牌轮胎，连续多年位列世界轮胎行业75强前25名。主要生产“河南”牌、“风神”牌工程机械轮胎、全钢载重子午线轮胎、斜交载重汽车轮胎、特种轮胎、轻卡轮胎、农用轮胎、军用轮胎等7大系列400多个规格品种。拥有博士后科研工作站和国家级企业技术中心。“风神”商标是中国驰名商标，“风神”牌全钢载重子午胎是中国名牌产品。", "风神轮胎",48,2,"699.00","899.00");
            Tire tire2 = new Tire(R.mipmap.luntai, "奔驰 G级(进口) 5.0L 自动 G500 标准型", "奔驰，德国汽车品牌，被认为是世界上最成功的高档汽车品牌之一，其完美的技术水平、过硬的质量标准、推陈出新的创新能力、以及一系列经典轿跑车款式令人称道。", "米其林轮胎",49,1," 2999.00","5999.00");
            Tire tire3 = new Tire(R.mipmap.luntai, "米其林轮胎001", "米其林公司创建于1889年的法国克莱蒙费朗。在100多年的时间中，米其林公司经历了持续不断的创新和发展。现拥有世界五大洲的业务运营以及位于欧洲、北美和亚洲的研发中心，并在全球超过170个国家中进行产品营销。米其林公司全球共有113529位员工、69家制造工厂和2个橡胶种植园。年产1.9亿条轮胎、1700万册地图和指南。", "米其林轮胎",50,0,"1099.00","1299.00");
            Tire tire4 = new Tire(R.mipmap.luntai, "保时捷 帕纳梅拉 3.0 THYBRID-自动 Pana 4", "保时捷股份公司是全球最大、历史最悠久的跑车制造商，也是最具盈利能力的汽车制造商。", "普利司通",50,0,"2300.00","2530.00");
            Tire tire5 = new Tire(R.mipmap.luntai, "普利司通001", "日本普利司通(BRIDGESTONE)公司是世界最大的轮胎及橡胶产品生产商，也是世界轮胎业三巨头之一。雄居世界橡胶业及轮胎业前列的普利司通集团，自1931年创建以来，始终奉行“以最高品质贡献社会”的企业方针，坚持为具有不同消费需求的用户提供所喜爱的商品。以领先于时代的尖端技术和积极果敢的进取精神，从开发、生产、销售、物流到售后服务均实行全方位的品质管理。普利司通作为享誉世界的跨国公司，目前销售区域遍布全球150多个国家，是世界上最大的轮胎制造厂商之一，并稳居财富杂志世界500强排名之列。", "普利司通",50,0,"599.00","499.00");
            Tire tire6 = new Tire(R.mipmap.luntai, "韩泰轮胎", "韩泰轮胎成立于1941年，是韩国的第一家轮胎企业。韩泰为乘用车、轻卡（SUV,RV等）、货车、巴士和专用赛车提供子午轮胎。韩泰在全球有五家研发中心，确保韩泰轮胎能够满足每个区域消费者的需求和提升消费者满意度。韩泰轮胎的产品遍及全球180个国家。它在全球的员工总数超过20,000名。韩泰轮胎于1996年进入中国。", "韩泰轮胎",35,3,"399.00","499.00");
            Tire tire7 = new Tire(R.mipmap.luntai, "横滨001", "日本横滨橡胶株式会社制造，成立于1917年总部设在日本横滨。该产品，1981年至今一直被澳门格兰披治大赛会指定为赛事轮胎，日本所有汽车制造商以及保时捷等，均使用横滨轮胎为标准轮胎。该产品率先获得我国商检部门CCIB认证。", "横滨轮胎",50,0,"300.00","400.00");
            Tire tire8 = new Tire(R.mipmap.luntai, "德国马牌001", "来自欧洲的汽车轮胎品牌德国马牌(Continental),产品包括越野车胎、轿车胎,为您提供冬季胎夏季胎各种轮胎保养知识,即刻与德国马牌一起体验非凡轮动!", "德国马牌",50,0,"500.00","600.00");

            list=new ArrayList<>();
            list.add(tire1);
            list.add(tire2);
            list.add(tire3);
            list.add(tire4);
            list.add(tire5);
            list.add(tire6);
            list.add(tire7);
            list.add(tire8);
            gridView.setAdapter(new ShopListAdapter(this,list,type));
        }else if(type==2){

            //补胎
            Repair repair1 = new Repair(R.mipmap.lingbu, "冷补", "冷补主要是通过冷补胶片和胶水来覆盖住原来气密层上的孔，实现修补的目的。", "补胎", 50, 0, "20.00", "30.00");
            Repair repair2 = new Repair(R.mipmap.rebu, "热补", "热补在很长一段时间里被认为是最好的补胎方式。", "补胎", 50, 0, "30.00", "40.00");
            Repair repair3 = new Repair(R.mipmap.moguding, "蘑菇钉补胎", "蘑菇钉的冠部起到和冷补胶片一样，能对轮胎气密层进行密封；蘑菇钉的柄则可以穿过钉孔对胎体橡胶进行修复。", "补胎", 50, 0, "60.00", "80.00");
            list=new ArrayList<>();
            list.add(repair1);
            list.add(repair2);
            list.add(repair3);
            gridView.setAdapter(new ShopListAdapter(this,list,type));

        }else if (type==3){
            //机油
            EngineOil en = new EngineOil(R.mipmap.jiyou, " 嘉实多机油", "嘉实多是世界公认的润滑油专家。BP（英国石油公司）旗下的一个子品牌。嘉实多的成功归功于Charles Cheers Wakefield，是他在1899年建立了专业的润滑油公司——嘉实多，并确立以技术先锋、获胜、热诚与激情、卓越表现为公司的核心价值，这些价值仍能代表嘉实多，而且是使嘉实多成功的核心力量。", "机油", 50, 0, "100.00", "150.00");
            list=new ArrayList<>();
            list.add(en);
            gridView.setAdapter(new ShopListAdapter(this,list,type));
        }

    }
}
