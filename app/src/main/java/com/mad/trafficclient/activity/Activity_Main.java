/**
 *
 */
package com.mad.trafficclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.App;
import com.mad.trafficclient.R;
import com.mad.trafficclient.fragment.FragmentHome;
import com.mad.trafficclient.fragment.Fragment_1;
import com.mad.trafficclient.fragment.Fragment_2;
import com.mad.trafficclient.fragment.Fragment_3;
import com.mad.trafficclient.fragment.Fragment_4;
import com.mad.trafficclient.fragment.Fragment_6;
import com.mad.trafficclient.fragment.Fragment_7;
import com.mad.trafficclient.fragment.Fragment_5;
import com.mad.trafficclient.fragment.Fragment_8;
import com.mad.trafficclient.fragment.Fragment_Idea;


/**
 * @author zhaowei
 */
public class Activity_Main extends FragmentActivity {
    private SlidingPaneLayout slidepanel;

    private Fragment fragment;

    private ListView listView;
    SimpleAdapter simpleAdapter;

    ArrayList<HashMap<String, Object>> actionItems;
    SimpleAdapter actionAdapter;

    public TextView tV_title;
    ImageView iVSliding;
    ImageView ivHome;

    private android.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        slidepanel = (SlidingPaneLayout) findViewById(R.id.slidingPL);

        listView = (ListView) findViewById(R.id.listView1);
        ivHome = (ImageView) findViewById(R.id.imageView_home);
        ivHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setHome();
            }
        });

        iVSliding = (ImageView) findViewById(R.id.imageView_Sliding);
        tV_title = (TextView) findViewById(R.id.tv_title);
        tV_title.setText(getString(R.string.app_title));


        iVSliding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (slidepanel.isOpen()) {
                    slidepanel.closePane();
                } else {
                    slidepanel.openPane();
                }
            }
        });
        final String[] actionTexts = new String[]{
                "账号管理",
                "公交查询",
                "红绿灯管理",
                "违章查询",
                "道路状况",
                "生活助手",
                "数据分析",
                "个人中心",
                "创新题",
                getString(R.string.res_left_exit)
        };
        int[] actionImages = new int[]{
                R.drawable.btn_l_star,
                R.drawable.btn_l_book,
                R.drawable.btn_l_slideshow,
                R.drawable.btn_l_target,
                R.drawable.btn_l_download,
                R.drawable.btn_l_star,
                R.drawable.btn_l_book,
                R.drawable.btn_l_slideshow,
                R.drawable.btn_l_target,
                R.drawable.btn_l_download
        };

        actionItems = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < actionImages.length; ++i) {
            HashMap<String, Object> item1 = new HashMap<String, Object>();
            item1.put("action_icon", actionImages[i]);
            item1.put("action_name", actionTexts[i]);
            actionItems.add(item1);
        }
        String userRole= App.appContext.getSharedPreferences("setting", Context.MODE_APPEND).getString("userRole", null);
        Log.e("userRole", userRole);
        if (TextUtils.equals("R01",userRole)){
            actionItems.remove(2);
        }
        actionAdapter = new SimpleAdapter(getApplicationContext(), actionItems, R.layout.left_list_fragment_item,
                new String[]{"action_icon", "action_name"},
                new int[]{R.id.recharge_method_icon, R.id.recharge_method_name});
        listView.setAdapter(actionAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map map=actionItems.get(arg2);
                String str= (String) map.get("action_name");
                Log.e("str", str);
                tV_title.setText(str);
                // TODO Auto-generated method stub
                if (str.equals("账号管理")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_1()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("公交查询")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_2()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("红绿灯管理")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_3()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("违章查询")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_4()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("道路状况")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_5()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("生活助手")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_6()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("数据分析")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_7()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("个人中心")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new Fragment_8()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("创新题")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.in, R.anim.out);
                    ft.replace(R.id.maincontent, new Fragment_Idea()).commit();
//                    tV_title.setText(actionTexts[arg2]);

                } else if (str.equals("用户退出")) {
                    exitAppDialog();

                } else {
                }
                slidepanel.closePane();
            }
        });

        fragmentManager = getFragmentManager();

        setHome();


    }

    public void setHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new FragmentHome()).commit();
        tV_title.setText(R.string.app_title);

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

        int[] listImg = new int[]{R.drawable.icon_trafic, R.drawable.icon_busstop, R.drawable.icon_lamp, R.drawable.icon_parking, R.drawable.icon_light, R.drawable.icon_etc, R.drawable.icon_speed};
        String[] listName = new String[]{"城市交通", "公交站点", "城市环境", "找车位", "红绿灯管理", "ETC管理", "高速车速监控"};
        for (int i = 0; i < listImg.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("itemImage", listImg[i]);
            item.put("itemName", listName[i]);
            items.add(item);
        }
        return items;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitAppDialog();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void exitAppDialog() {
        new AlertDialog.Builder(this)
                // .setIcon(android.R.drawable.ic_menu_info_detailsp)
                .setTitle("提示").setMessage("你确定要退出吗").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        }).show();

    }


}
