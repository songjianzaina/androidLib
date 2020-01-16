package com.inswork.indexbar.bank;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.inswork.indexbar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 该Activity属于原生页面
 *
 */
public class BankQuickActivity extends Activity {

    private static OnItemClickListener listener;
    private QuickIndexBar quickIndexBar;
    private List<BankBean> list = new ArrayList<BankBean>();
    private ListView listview;
    private TextView currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_quick);
        initView(savedInstanceState);
        setListener();
        initData();
    }


    protected void initView(Bundle savedInstanceState) {
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        currentIndex = (TextView) findViewById(R.id.currentIndex);
    }

    protected void initData() {
        fillList();
        Collections.sort(list);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new MyAdapter());
    }

    protected void setListener() {
        quickIndexBar.setOnTouchIndexListener(new QuickIndexBar.OnTouchIndexListener() {

            @Override
            public void onTouchIndex(String word) {
                for (int i = 0; i < list.size(); i++) {
                    String firstWord = list.get(i).getPinyin().charAt(0) + "";
                    if (firstWord.equals(word)) {
                        listview.setSelection(i);
                        break;
                    }
                    showIndex(word);
                }
            }
        });
    }


    private Handler handler = new Handler();

    /**
     * 显示触按的字母
     *
     * @param word
     */
    protected void showIndex(String word) {
        currentIndex.setVisibility(View.VISIBLE);
        currentIndex.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                currentIndex.setVisibility(View.GONE);
            }
        }, 1000);
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(BankQuickActivity.this,
                        R.layout.adapter_list, null);
                holder = new ViewHolder();
                holder.index = (TextView) convertView.findViewById(R.id.index);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }
            final BankBean friend = list.get(position);
            String currentWord = friend.getPinyin().charAt(0) + "";
            if (position > 0) {
                String lastword = list.get(position - 1).getPinyin().charAt(0)
                        + "";
                if (currentWord.equals(lastword)) {
                    holder.index.setVisibility(View.GONE);
                } else {
                    holder.index.setVisibility(View.VISIBLE);
                    holder.index.setText(currentWord);
                }
            } else {
                holder.index.setVisibility(View.VISIBLE);
                holder.index.setText(currentWord);
            }
            holder.name.setText(friend.getName());
            //加载银行图标
            Drawable drawable = getResources().getDrawable(friend.getIcon());
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.name.setCompoundDrawables(drawable, null, null, null);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将选中的条目传递出去
//                    BusEvent.sendSticky(friend);
                    //跳转至银行选择界面
//                    startActivity(new Intent(getApplication(), EditSettlementActivity.class));
                    if (listener != null) {
                        listener.onItemClick(friend);
                        finish();
                    }

                }
            });
            return convertView;
        }

    }

    class ViewHolder {
        private TextView index;
        private TextView name;
    }

    private void fillList() {
        // 银行数据
        list.add(new BankBean("北京农商银行", R.mipmap.bank1_beijingnongshang));
        list.add(new BankBean("包商银行", R.mipmap.baoshangyinhang1));
        list.add(new BankBean("渤海银行", R.mipmap.bohaiyinhang1));
        list.add(new BankBean("北京银行", R.mipmap.bank1_beijing));
        list.add(new BankBean("长沙银行", R.mipmap.bank1_changsha));
        list.add(new BankBean("成都农商银行", R.mipmap.bank1_chengdunongshang));
        list.add(new BankBean("成都银行", R.mipmap.bank1_chengdu));
        list.add(new BankBean("重庆银行", R.mipmap.bank1_chongqing));

        list.add(new BankBean("大连银行", R.mipmap.bank1_dalian));
        list.add(new BankBean("东亚银行", R.mipmap.dongyayinhang1));
        list.add(new BankBean("东莞农商银行", R.mipmap.dongguannonghsangyinhang1));
        list.add(new BankBean("东莞银行", R.mipmap.bank1_dongguan));

        list.add(new BankBean("福建农信", R.mipmap.nongcunxinyongshe1));
        list.add(new BankBean("甘肃银行", R.mipmap.bank1_gansu));
        list.add(new BankBean("广州银行", R.mipmap.bank1_guangzhou));
        list.add(new BankBean("广州农商银行", R.mipmap.bank1_guangzhounongshang));
        list.add(new BankBean("工商银行", R.mipmap.bank1_gongshang));
        list.add(new BankBean("广发银行", R.mipmap.bank1_guangfa));
        list.add(new BankBean("光大银行", R.mipmap.bank1_guangda));

        list.add(new BankBean("杭州银行", R.mipmap.bank1_hangzhou));
        list.add(new BankBean("花旗银行", R.mipmap.huaqiyinhang1));
        list.add(new BankBean("哈尔冰银行", R.mipmap.huaerbinyinhang1));
        list.add(new BankBean("汇丰银行", R.mipmap.huifengyinhang1));
        list.add(new BankBean("河北银行", R.mipmap.bank1_heibei));
        list.add(new BankBean("恒丰银行", R.mipmap.hengfengyinhang1));
        list.add(new BankBean("华夏银行", R.mipmap.bank1_huaxia));

        list.add(new BankBean("吉林银行", R.mipmap.bank1_jilin));
        list.add(new BankBean("江苏银行", R.mipmap.bank1_jiangsu));
        list.add(new BankBean("建设银行", R.mipmap.bank1_jianshe));
        list.add(new BankBean("交通银行", R.mipmap.bank1_jiaotong));
        list.add(new BankBean("江南农商银行", R.mipmap.jiangnannongcunshangyeyinhang1));

        list.add(new BankBean("昆仑银行", R.mipmap.hunlunyinhang1));

        list.add(new BankBean("兰州银行", R.mipmap.bank1_lanzhou));

        list.add(new BankBean("民泰银行", R.mipmap.bank1_mintai));
        list.add(new BankBean("民生银行", R.mipmap.bank1_minsheng));

        list.add(new BankBean("南京银行", R.mipmap.bank1_nanjing));
        list.add(new BankBean("农村信用社", R.mipmap.nongcunxinyongshe1));
        list.add(new BankBean("内蒙古银行", R.mipmap.bank1_neimenggu));
        list.add(new BankBean("宁波银行", R.mipmap.bank1_ningbo));
        list.add(new BankBean("宁夏银行", R.mipmap.bank1_ningxia));
        list.add(new BankBean("农业银行", R.mipmap.bank1_nongye));

        list.add(new BankBean("平安银行", R.mipmap.bank1_zhongguopingan));
        list.add(new BankBean("浦东发展银行", R.mipmap.bank1_pufa));
        list.add(new BankBean("齐鲁银行", R.mipmap.bank1_qilu));
        list.add(new BankBean("青岛银行", R.mipmap.bank1_qingdao));
        list.add(new BankBean("青海银行", R.mipmap.bank1_qinghai));
        list.add(new BankBean("其他银行", R.mipmap.bank1_qita));

        list.add(new BankBean("上海农商银行", R.mipmap.bank1_shanghainongshang));
        list.add(new BankBean("上海银行", R.mipmap.bank1_shanghai));
        list.add(new BankBean("深圳农商银行", R.mipmap.shenzhennongshangyinhang1));
        list.add(new BankBean("山东农商银行", R.mipmap.shandongnonghsangyinhang1));
        list.add(new BankBean("盛京银行", R.mipmap.shengjingyinhang1));

        list.add(new BankBean("天津银行", R.mipmap.bank1_tianjin));
        list.add(new BankBean("天津农商银行", R.mipmap.tianjinnongshangyinhang1));

        list.add(new BankBean("温州银行", R.mipmap.bank1_wenzhou));

        list.add(new BankBean("兴业银行", R.mipmap.bank1_xingye));

        list.add(new BankBean("邮政储蓄", R.mipmap.bank1_youzheng));

        list.add(new BankBean("浙商银行", R.mipmap.bank1_zheshang));
        list.add(new BankBean("中国银行", R.mipmap.bank1_zhongguo));
        list.add(new BankBean("招商银行", R.mipmap.bank1_zhaoshang));
        list.add(new BankBean("中信银行", R.mipmap.bank1_zhongxin));
        list.add(new BankBean("浙江农信", R.mipmap.zhejiangnongxin1));
        list.add(new BankBean("渣打银行", R.mipmap.zhadayinhang1));
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {

        BankQuickActivity.listener = listener;
    }
}