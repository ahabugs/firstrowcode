package com.example.a14_coolweather;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14_coolweather.db.City;
import com.example.a14_coolweather.db.Province;
import com.example.a14_coolweather.db.Town;
import com.example.a14_coolweather.util.HttpUtil;
import com.example.a14_coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//import okhttp3.internal.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseAreaFrag extends Fragment {
    private static final String TAG = "ChooseAreaFragDebug";
    private Button button_back;
    private TextView textViewTitle;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> dataList = new ArrayList<>();


    private static final int LEV_PROV = 1;
    private static final int LEV_CITY= 2;
    private static final int LEV_TOWN = 3;
    private int currentLevel = 0;


    private List<Province> provinceList;
    private List<City> cityList;
    private List<Town> townList;
    private Province selectedProv;
    private City selectedCity;
    private Town selectedTown;
    private int selectedProvIndex = 0;
    private int selectedCityIndex = 0;
    private int selectedTownIndex = 0;
    private String responseData;
    private Context mContext = null;
    private Activity mActivity = null;

    private ProgressDialog progressDialog;



    public ChooseAreaFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (mContext == null)
//            mContext = MyApplication.getContext();
        Log.e(TAG, "onAttach: " + "context=" + context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: getActivity=" + getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        button_back = (Button) view.findViewById(R.id.button_back);
        textViewTitle = (TextView)view.findViewById(R.id.text_view_title);
        listView = (ListView)view.findViewById(R.id.list_view);
        /*
        * call getContext() requires API Level 23, but mini 22 is defined in Manifest */
        /*arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, dataList);*/

        arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: " + getActivity());
        super.onActivityCreated(savedInstanceState);
        // 加载省份
        queryProvinces();

        // 监听标题栏返回按钮
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentLevel) {
                    case LEV_TOWN:
                        queryCities(selectedProv);
                        break;
                    case LEV_CITY:
                        queryProvinces();
                        break;
                    case LEV_PROV:
                    default:
                        selectedProvIndex = 0;
                        selectedCityIndex = 0;
                        break;
                }
            }
        });

        // 监听列表项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (currentLevel) {
                    case LEV_PROV:
                        // Province selectedP = getSelectedProv(position);
                        // 保留index，以便返回上一层后省份列表可直接定位到原来省份的位置
                        selectedProvIndex = position;
                        // selectedCityIndex = 0;
                        selectedProv = getSelectedProv(position);
                        queryCities(selectedProv);
                        break;
                    case LEV_CITY:
                        // City selectedC = getSelectedCity(position);
                        selectedCityIndex = position;
                        selectedCity =  getSelectedCity(position);
                        queryTowns(selectedCity);
                        break;
                    case LEV_TOWN:
                        // 只能显示镇，区的天气，不能显示一个城市的天气
                        selectedTown = getSelectedTown(position);
                        Activity activity = getActivity();
                        if (activity instanceof MainActivity) {
                            Intent intent = new Intent(ChooseAreaFrag.this.getActivity(),
                                    WeatherActivity.class);
                            intent.putExtra("weather_id", selectedTown.getWeatherId());
                            startActivity(intent);
                            getActivity().finish();
                        } else if (activity instanceof WeatherActivity) {
                            WeatherActivity weatherActivity = (WeatherActivity)activity;
                            DrawerLayout layout = weatherActivity.getDrawerLayoutWeather();
                            SwipeRefreshLayout swipeRefreshLayout = weatherActivity.
                                    getSwipeRefreshLayout();
                            layout.closeDrawers();
                            swipeRefreshLayout.setRefreshing(true);
                            weatherActivity.setSwipeRefreshWeatherId(selectedTown.getWeatherId());
                            weatherActivity.requestWeatherData();
                            weatherActivity.setPicBackground();
                        }

                    default:
                        break;
                }
            }
        });

    }


    // 一启动程序后，不可以立即（瞬间，很断的时间内）与服务器建立连接，否则会出现连接失败。
    // 可能是socket异常。稍微在省份界面等一下，然后再点城市。
    private void queryFromServer(String address, final int type) {
        // 显示进度条
        showProgressDialog();

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ChooseAreaFrag.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(mContext, "加载失败", Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            // 子线程
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseData = response.body().string();
                response.body().close();

                // 数据插入数据库
                boolean result = false;
                if (LEV_PROV == type) {
                    result = Utility.handleProvinceResp(responseData);
                } else if (LEV_CITY == type) {
                    int provCode = selectedProv.getCode();
                    result = Utility.handleCityResp(responseData, provCode);
                } else if (LEV_TOWN == type) {
                    /*
                    * 此处用else if 结合result = false可加强代码健壮性。如，type传入值不等于这3种情况,
                    * 程序依然可以正确处理
                    * */
                    int cityCode = selectedCity.getCode();
                    result = Utility.handleTownResp(responseData, cityCode);
                }

                // 解析失败导致进度条不能关闭
                /*if (result) {
                    ChooseAreaFrag.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 重新查一次数据库并加载listView列表
                            closeProgressDialog();
                            queryFromDB(type);
                        }
                    });
                }*/

                final String boolString = Boolean.toString(result);
                ChooseAreaFrag.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 重新查一次数据库并加载listView列表
                        closeProgressDialog();
                        if (Boolean.parseBoolean(boolString))  {
                            queryFromDB(type);
                        } else
                        {
                            Toast.makeText(mContext, "处理失败", Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), "处理失败", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    /*
    *
    * 查数据库，返回省市镇provinceList, cityList, townList，并填充dataList
    * */
    private boolean queryFromDB(int type) {

        if (type == LEV_PROV) {
            provinceList = DataSupport.findAll(Province.class);
            if (provinceList.size() > 0) {
                // return provinceList;
                // 加载省份列表
                dataList.clear();
                for (Province p : provinceList) {
                    dataList.add(p.getName());
                }
                arrayAdapter.notifyDataSetChanged();
                // listView.setSelection(0);
                listView.setSelection(selectedProvIndex);
                currentLevel = LEV_PROV;
                return true;
            }
        } else if (type == LEV_CITY) {
            cityList = DataSupport.where("provinceCode = ?",
                    String.valueOf(selectedProv.getCode())).find(City.class);
            if (cityList.size() > 0) {
                textViewTitle.setText(selectedProv.getName());
                button_back.setVisibility(View.VISIBLE);
                dataList.clear();
                for (City city : cityList) {
                    dataList.add(city.getName());
                }
                arrayAdapter.notifyDataSetChanged();
                // listView.setSelection(0);
                listView.setSelection(selectedCityIndex);
                currentLevel = LEV_CITY;
                return true;
            }
        } else if (type == LEV_TOWN) {
            townList = DataSupport.where("cityCode = ?",
                    String.valueOf(selectedCity.getCode())).find(Town.class);
            if (townList.size() > 0) {
                textViewTitle.setText(selectedCity.getName());
                button_back.setVisibility(View.VISIBLE);
                dataList.clear();
                for (Town town : townList) {
                    dataList.add(town.getName());
                }
                arrayAdapter.notifyDataSetChanged();
                listView.setSelection(0);
                currentLevel = LEV_TOWN;
                return true;
            }
        }

        return false;
    }

    public void queryProvinces() {
        textViewTitle.setText("中国");
        button_back.setVisibility(View.GONE);

        if (!queryFromDB(LEV_PROV)) {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, LEV_PROV);
        }/* else {
            currentLevel = LEV_PROV;
        }*/
    }

    public void queryCities(Province selectedProv) {
        // 查询失败后，标题不应该切换成城市
/*        textViewTitle.setText(selectedProv.getName());
        button_back.setVisibility(View.VISIBLE);*/

        if (!queryFromDB(LEV_CITY)) {
            int provCode = selectedProv.getCode();
            String address = "http://guolin.tech/api/china/" + provCode;
            queryFromServer(address, LEV_CITY);
        }/* else {
            currentLevel = LEV_CITY;
        }*/
    }

    public void queryTowns(City selectedCity) {
     /*   textViewTitle.setText(selectedCity.getName());
        button_back.setVisibility(View.VISIBLE);*/

        if (!queryFromDB(LEV_TOWN)) {
            int provCode = selectedProv.getCode();
            int cityCode = selectedCity.getCode();
            String address = "http://guolin.tech/api/china/" + provCode + "/" + cityCode;
            queryFromServer(address, LEV_TOWN);
        }/* else {
            currentLevel = LEV_TOWN;
        }*/
    }


    public Province getSelectedProv(int index) {
        // provinceList在查数据库成功后被赋值
        return provinceList.get(index);
    }

    public City getSelectedCity(int index) {
        return cityList.get(index);
    }


    public Town getSelectedTown(int index) {
        return townList.get(index);
    }

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
            return;
        }

        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("");
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
