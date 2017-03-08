package car.com.wlc.cardemo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.GridAdapter;
import car.com.wlc.cardemo.view.LocalImageHolderView;


public class CarShopActivity extends AppCompatActivity {

    private ConvenientBanner mBanner;
    private ArrayList<Integer> imlist;
    private GridView mGridView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean 汽车快修;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //初始化数据
        initImData();
        initView();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initImData() {
        imlist = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            imlist.add(R.mipmap.banners);

        }
    }

    private void initView() {
        mBanner = ((ConvenientBanner) findViewById(R.id.carshop_banner));
        mGridView = ((GridView) findViewById(R.id.carshop_girdview));
        findViewById(R.id.car_shop_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initBanner();
        initGrView();
    }

    private void initGrView() {
        List<Integer> list = new ArrayList();

        list.add(R.mipmap.one);
        list.add(R.mipmap.two);
        list.add(R.mipmap.three);
        list.add(R.mipmap.fore);
        list.add(R.mipmap.five);
        list.add(R.mipmap.six);
        list.add(R.mipmap.seven);
        list.add(R.mipmap.eight);

        List<String> list1 = new ArrayList();
        list1.add("汽车快修");
        list1.add("汽车美容");
        list1.add("汽车轮胎");
        list1.add("汽车机油");
        list1.add("汽车租赁");
        list1.add("汽车估值");
        list1.add("汽车保险");
        list1.add("更多服务");

        GridAdapter gridAdapter = new GridAdapter(this,list,list1);
        mGridView.setAdapter(gridAdapter);


    }

    private void initBanner() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imlist)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.indicator_no_select, R.drawable.indicator_select})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。//        convenientBanner.setManualPageable(false);//设置不能手动影响

    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        mBanner.startTurning(3000);
    }
    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        mBanner.stopTurning();
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CarShop Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

//    class LocalImageHolderView implements Holder<Integer> {
//        private ImageView imageView;
//
//        @Override
//        public View createView(Context context) {
//            imageView = new ImageView(context);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            return imageView;
//        }
//
//        @Override
//        public void UpdateUI(Context context, final int position, Integer data) {
//            imageView.setImageResource(data);
//        }
//    }

}
