package com.kongnan.sidebar.Demo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.kongnan.sidebar.R;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListView nameList; // 显示所有数据的ListView
    private SideBar sideBar; // 右边的索引条
    private TextView indexViewer; // 位于屏幕中间的当前选中的索引的放大

    private List<DataModel> dataList; // 用来存放所有数据的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        nameList = (ListView) findViewById(R.id.find_main_lv_names);
        sideBar = (SideBar) findViewById(R.id.find_main_ly_indexes);
        indexViewer = (TextView) findViewById(R.id.find_main_tv_indexviewer);
        sideBar.setIndexViewer(indexViewer);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 初始化存放所有信息的List并添加所有数据
        dataList = new ArrayList<DataModel>();
        String[] names = getResources().getStringArray(R.array.names);
        for (String name : names) {
            dataList.add(new DataModel(name, getPinYinHeadChar(name)));
        }
        // 对dataList进行排序：根据每条数据的indexName（字符串）属性进行排序
        Collections.sort(dataList, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel model1, DataModel model2) {
                return String.valueOf(model1.getIndexName()).compareTo(String.valueOf(model2.getIndexName()));
            }
        });
        MyAdapter adapter = new MyAdapter(Main2Activity.this, dataList);
        nameList.setAdapter(adapter);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        // 回调接口将ListView滑动到选中的索引指向的第一条数据
        // 如果选中的索引没有对应任何数据，则指向这个索引上面的最近的有数据的索引
        sideBar.setOnIndexChoiceChangedListener(new SideBar.OnIndexChoiceChangedListener() {
            @Override
            public void onIndexChoiceChanged(String s) {
                char indexName = s.charAt(0);
                int lastIndex = 0;
                for (int i = 1; i < dataList.size(); i++) {
                    char currentIndexName = dataList.get(i).getIndexName();
                    if (currentIndexName >= indexName) {
                        if (currentIndexName == indexName) {
                            nameList.setSelection(i);
                            if (currentIndexName == '#') {
                                nameList.setSelection(0);
                            }
                        } else {
                            nameList.setSelection(lastIndex);
                        }
                        break;
                    }
                    if (dataList.get(i - 1).getIndexName() != currentIndexName) {
                        lastIndex = i;
                    }
                }
            }
        });
    }

    /**
     * 返回中文的首字母（大写字母），如果str不是汉字，则返回 “#”
     */
    public static char getPinYinHeadChar(String str) {
        char result = str.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(result);
        if (pinyinArray != null) {
            result = pinyinArray[0].charAt(0);
        }
        if (!(result >= 'A' && result <= 'Z' || result >= 'a' && result <= 'z')) {
            result = '#';
        }
        return String.valueOf(result).toUpperCase().charAt(0);
    }
}
