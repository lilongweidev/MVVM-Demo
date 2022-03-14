package com.llw.mvvm.ui.fragment;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.DialogWeatherBinding;
import com.llw.mvvm.databinding.MapFragmentBinding;
import com.llw.mvvm.model.LiveWeather;
import com.llw.mvvm.ui.adapter.CityAdapter;
import com.llw.mvvm.ui.adapter.ForecastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图
 *
 * @author llw
 */
public class MapFragment extends BaseFragment implements AMap.OnMyLocationChangeListener,
        GeocodeSearch.OnGeocodeSearchListener, WeatherSearch.OnWeatherSearchListener,
        DistrictSearch.OnDistrictSearchListener {

    private static final String TAG = MapFragment.class.getSimpleName();
    private MapFragmentBinding binding;
    //解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;

    //地图对象
    private AMap aMap;

    private GeocodeSearch geocoderSearch = null;
    private String district = null;// 区/县

    private LocalWeatherLive liveResult;
    //天气预报列表
    private List<LocalDayWeatherForecast> weatherForecast;

    //地区搜索
    private DistrictSearch districtSearch;
    //地区搜索查询
    private DistrictSearchQuery districtSearchQuery;

    //数组下标
    private int index = 0;
    //行政区数组
    private final String[] districtArray = new String[5];


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //基于个人隐私保护的关系，这里要设置为true，否则会出现地图白屏的情况
        MapsInitializer.updatePrivacyShow(requireActivity(), true, true);
        MapsInitializer.updatePrivacyAgree(requireActivity(), true);
        binding.mapView.onCreate(savedInstanceState);
        //显示加载弹窗
        showLoading();
        //点击按钮显示天气弹窗
        binding.fabWeather.setOnClickListener(v -> showWeatherDialog());
        //点击按钮显示城市弹窗
        binding.fabCity.setOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.END));
        //抽屉菜单监听
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull @NotNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull @NotNull View drawerView) {
                binding.fabCity.hide();
            }

            @Override
            public void onDrawerClosed(@NonNull @NotNull View drawerView) {
                binding.fabCity.show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        initMap();
        initSearch();
        //搜索行政区
        districtArray[index] = "中国";
        districtSearch(districtArray[index]);
    }

    /**
     * 行政区搜索
     */
    public void districtSearch(String name) {
        binding.setName(name);
        //设置查询关键字
        districtSearchQuery.setKeywords(name);
        districtSearch.setQuery(districtSearchQuery);
        // 异步查询行政区
        districtSearch.searchDistrictAsyn();
    }

    /**
     * 显示天气弹窗
     */
    private void showWeatherDialog() {
        //隐藏浮动按钮
        binding.fabWeather.hide();

        BottomSheetDialog dialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogStyle_Light);
        DialogWeatherBinding weatherBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.dialog_weather, null, false);
        //设置数据源
        weatherBinding.setLiveWeather(new LiveWeather(district, liveResult));
        //配置天气预报列表
        ForecastAdapter forecastAdapter = new ForecastAdapter(weatherForecast);
        weatherBinding.rvForecast.setLayoutManager(new LinearLayoutManager(requireActivity()));
        weatherBinding.rvForecast.setAdapter(forecastAdapter);

        dialog.setContentView(weatherBinding.getRoot());
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        //弹窗关闭时显示浮动按钮
        dialog.setOnDismissListener(dialog1 -> binding.fabWeather.show());
        dialog.show();
    }

    /**
     * 初始化搜索
     */
    private void initSearch() {
        try {
            //初始化地理编码搜索
            geocoderSearch = new GeocodeSearch(requireActivity());
            geocoderSearch.setOnGeocodeSearchListener(this);
            //初始化城市行政区搜索
            districtSearch = new DistrictSearch(requireActivity());
            districtSearch.setOnDistrictSearchListener(this);
            districtSearchQuery = new DistrictSearchQuery();
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        //初始化地图控制器对象
        aMap = binding.mapView.getMap();
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        MyLocationStyle style = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(style);//设置定位蓝点的Style
        //修改放大缩小按钮的位置
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        //设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
        //设置地图是否为夜间模式
        aMap.setMapType(isNight() ? AMap.MAP_TYPE_NIGHT : AMap.MAP_TYPE_NORMAL);
    }

    /**
     * 搜索天气
     *
     * @param type WEATHER_TYPE_LIVE 实时天气   WEATHER_TYPE_FORECAST  预报天气
     */
    private void searchWeather(int type) {
        WeatherSearchQuery weatherSearchQuery = new WeatherSearchQuery(district, type);
        try {
            WeatherSearch weatherSearch = new WeatherSearch(requireActivity());
            weatherSearch.setOnWeatherSearchListener(this);
            weatherSearch.setQuery(weatherSearchQuery);
            weatherSearch.searchWeatherAsyn(); //异步搜索
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if (location != null) {
            //Log.e(TAG, "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());

            //创建一个经纬度点，参数一是纬度，参数二是经度
            LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
            //通过经纬度获取地址信息
            geocoderSearch.getFromLocationAsyn(query);
        } else {
            Log.e(TAG, "定位失败");
        }
    }


    /**
     * 坐标转地址
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        //解析result获取地址描述信息
        if (rCode == PARSE_SUCCESS_CODE) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            //显示解析后的地址
            //Log.e(TAG, "地址: " + regeocodeAddress.getFormatAddress());
            district = regeocodeAddress.getDistrict();
            Log.e(TAG, "区: " + district);

            //搜索天气  实时天气和预报天气
            searchWeather(WeatherSearchQuery.WEATHER_TYPE_LIVE);
            searchWeather(WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        } else {
            showMsg("获取地址失败");
        }
    }

    /**
     * 地址转坐标
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
        //拿到返回的坐标，然后去地图上定位，改变地图中心
        if (rCode == PARSE_SUCCESS_CODE) {
            Log.e(TAG, "onGeocodeSearched: 地址转坐标成功");
            List<GeocodeAddress> geocodeAddressList = geocodeResult.getGeocodeAddressList();
            if (geocodeAddressList != null && geocodeAddressList.size() > 0) {
                LatLonPoint latLonPoint = geocodeAddressList.get(0).getLatLonPoint();
                Log.e(TAG, "onGeocodeSearched: 坐标：" + latLonPoint.getLongitude() + "，" + latLonPoint.getLatitude());
                //切换地图中心
                switchMapCenter(geocodeResult, latLonPoint);
            }
        } else {
            showMsg("获取坐标失败");
        }
    }

    /**
     * 切换地图中心
     */
    private void switchMapCenter(GeocodeResult geocodeResult, LatLonPoint latLonPoint) {
        //显示解析后的坐标，
        double latitude = latLonPoint.getLatitude();
        double longitude = latLonPoint.getLongitude();
        //创建经纬度对象
        LatLng latLng = new LatLng(latitude, longitude);
        //改变地图中心点
        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 18, 30, 0));
        //在地图上添加marker
        aMap.addMarker(new MarkerOptions().position(latLng).title(geocodeResult.getGeocodeQuery().getLocationName()).snippet("DefaultMarker"));
        //动画移动
        aMap.animateCamera(mCameraUpdate);

        //移动地图后通过坐标转地址，触发onRegeocodeSearched回调，在这个回调里去查询天气
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 实时天气返回
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int code) {
        liveResult = localWeatherLiveResult.getLiveResult();
        if (liveResult == null) {
            showMsg("实时天气数据为空");
        }
    }

    /**
     * 天气预报返回
     */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int code) {
        weatherForecast = localWeatherForecastResult.getForecastResult().getWeatherForecast();
        if (weatherForecast != null) {
            binding.fabWeather.show();
            binding.fabCity.show();
            //隐藏加载弹窗
            dismissLoading();
        } else {
            showMsg("天气预报数据为空");
        }
    }

    /**
     * 行政区搜索返回
     *
     * @param districtResult 搜索结果
     */
    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        if (districtResult != null) {
            dismissLoading();
            if (districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
                final List<String> nameList = new ArrayList<>();

                List<DistrictItem> subDistrict1 = districtResult.getDistrict().get(0).getSubDistrict();
                for (int i = 0; i < subDistrict1.size(); i++) {
                    String name = subDistrict1.get(i).getName();
                    nameList.add(name);
                }
                Log.e(TAG, "onDistrictSearched: " + subDistrict1.size());
                for (DistrictItem districtItem : subDistrict1) {
                    Log.e(TAG, "onDistrictSearched: "+districtItem.getName());
                }
                //设置数据源
                if (nameList.size() != 0) {
                    //设置数据源
                    CityAdapter cityAdapter = new CityAdapter(nameList);
                    binding.rvCity.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    //item点击事件
                    cityAdapter.setOnItemClickListener((adapter, view, position) -> {
                        showLoading();

                        index++;
                        districtArray[index] = nameList.get(position);

                        binding.ivBack.setVisibility(View.VISIBLE);
                        //返回键的监听
                        binding.ivBack.setOnClickListener(v -> {
                            index--;
                            //搜索上级行政区
                            districtSearch(districtArray[index]);
                            if ("中国".equals(districtArray[index])) {
                                binding.ivBack.setVisibility(View.GONE);
                            }
                        });
                        //搜索此区域的下级行政区
                        districtSearch(districtArray[index]);
                    });
                    binding.rvCity.setAdapter(cityAdapter);
                } else {
                    //通过地址得到坐标
                    addressToLatlng();
                }
            } else {
                showMsg(districtResult.getAMapException().getErrorCode() + "");
            }
        }
    }

    /**
     * 地址转经纬度坐标
     */
    private void addressToLatlng() {
        showLoading();
        //关闭抽屉
        binding.drawerLayout.closeDrawer(GravityCompat.END);
        // GeocodeQuery 有两个参数 一个是当前所选城市，第二个是当前地的上级城市，
        Log.e(TAG, "onDistrictSearched: " + districtArray[index] + "  ,  " + districtArray[index - 2]);
        GeocodeQuery query = new GeocodeQuery(districtArray[index], districtArray[index - 2]);
        geocoderSearch.getFromLocationNameAsyn(query);

        //重置行政区
        index = 0;
        //搜索行政区
        districtArray[index] = "中国";
        districtSearch(districtArray[index]);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }
}