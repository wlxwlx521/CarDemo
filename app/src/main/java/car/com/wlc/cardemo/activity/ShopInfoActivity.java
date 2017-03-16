package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.fragment.BlankFragment;
import car.com.wlc.cardemo.javaBean.Product;

import static com.amap.api.mapcore.util.af.a.n;

public class ShopInfoActivity extends AppCompatActivity {


    private Product product;
    private ConvenientBanner banner;
    private TextView name;
    private TextView price;
    private TextView oprice;
    private TextView inventory;
    private TextView sale;
    private List localImages;
    private android.support.design.widget.TabLayout tab;
    private ViewPager vp;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private NumberPicker number;
    private TextView buynamer;
    private TextView xuanzenumber;
    private Button buy_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        intData();
        initView();
    }

    private void initView() {
        banner = ((ConvenientBanner) findViewById(R.id.shop_info_banner));
        name = ((TextView) findViewById(R.id.shop_info_name));
        price = ((TextView) findViewById(R.id.shop_info_price));
        oprice = ((TextView) findViewById(R.id.shop_info_oprice));
        inventory = ((TextView) findViewById(R.id.shop_info_inventory));
        sale = ((TextView) findViewById(R.id.shop_info_sale));
        buynamer = ((TextView) findViewById(R.id.shop_ifo_buynamer));
        xuanzenumber = ((TextView) findViewById(R.id.shop_ifo_xuanzenumber));
        buy_btn = ((Button) findViewById(R.id.shop_info_buy));
        findViewById(R.id.shop_info_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(buynamer.getText().equals(""))) {
                    finish();
                    startActivity(new Intent(ShopInfoActivity.this, BuySuccessActivity.class));
                }else {
                    Toast.makeText(ShopInfoActivity.this,"请选择商品数量",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.shop_info_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v, product.inventory);
            }
        });


        tab = ((android.support.design.widget.TabLayout) findViewById(R.id.shop_info_tab));
        vp = ((ViewPager) findViewById(R.id.shop_info_vp));
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });


        tab.setupWithViewPager(vp);

        name.setText(product.name);
        price.setText(product.price);
        oprice.setText(product.oprice);
        inventory.setText(product.inventory + "");
        sale.setText(product.sale + "");


        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        banner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.indicator_no_select, R.drawable.indicator_select})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);


    }

    private void intData() {

        Intent intent = getIntent();
        product = ((Product) intent.getSerializableExtra("product"));
        localImages = new ArrayList();
        localImages.add(product.image);
        localImages.add(product.image);

        titles = new ArrayList<>();
        titles.add("图文详情");
        titles.add("用户评价");
        titles.add("同店推荐");

        fragments = new ArrayList<>();
        BlankFragment blankFragment0 = new BlankFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putString("info", product.info);
        blankFragment0.setArguments(bundle0);

        BlankFragment blankFragment1 = new BlankFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("info", "暂时没有用户评价");
        blankFragment1.setArguments(bundle1);
        BlankFragment blankFragment2 = new BlankFragment();

        Bundle bundle2 = new Bundle();
        bundle2.putString("info", "暂时没有同店推荐");
        blankFragment2.setArguments(bundle2);
        fragments.add(blankFragment0);
        fragments.add(blankFragment1);
        fragments.add(blankFragment2);


    }

    public void setText(String s) {
        xuanzenumber.setText("数量：");
        buynamer.setText(s);
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private void showPopupWindow(final View view, int maxnumber) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(ShopInfoActivity.this).inflate(
                R.layout.pop_window, null);
        // 设置按钮的点击事件
        Button button = (Button) contentView.findViewById(R.id.pop_btn);
        number = ((NumberPicker) contentView.findViewById(R.id.number_picker));

        number.setMaxValue(maxnumber);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int value = number.getValue();
                setText("" + value);
                popupWindow.dismiss();

            }
        });
        contentView.findViewById(R.id.pop_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.custom_info_bubble));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
}
