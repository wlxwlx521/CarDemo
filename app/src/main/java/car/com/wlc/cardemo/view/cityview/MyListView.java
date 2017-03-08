package car.com.wlc.cardemo.view.cityview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView，解决ScrollView嵌套自定义ListView只显示一行半
 */
public class MyListView extends ListView {

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context) {
		super(context);
	}

	/**
	 * 其中onMeasure函数决定了组件显示的高度与宽度；
	 * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
	 * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 9,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
