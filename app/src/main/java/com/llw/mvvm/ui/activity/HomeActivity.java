package com.llw.mvvm.ui.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps.MapsInitializer;
import com.google.gson.Gson;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityHomeBinding;
import com.llw.mvvm.databinding.DialogEditBinding;
import com.llw.mvvm.databinding.DialogModifyUserInfoBinding;
import com.llw.mvvm.databinding.NavHeaderBinding;
import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.utils.CameraUtils;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.EasyDate;
import com.llw.mvvm.utils.MVUtils;
import com.llw.mvvm.utils.PermissionUtils;
import com.llw.mvvm.utils.SizeUtils;
import com.llw.mvvm.view.dialog.AlertDialog;
import com.llw.mvvm.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 主页面
 *
 * @author llw
 */
@SuppressLint("NonConstantResourceId")
@AndroidEntryPoint
public class HomeActivity extends BaseActivity {

    @Inject
    MVUtils mvUtils;

    private static final String TAG = HomeActivity.class.getSimpleName();

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;

    private User localUser;

    //可输入弹窗
    private AlertDialog editDialog = null;
    //修改用户信息弹窗
    private AlertDialog modifyUserInfoDialog = null;
    //是否显示修改头像的两种方式
    private boolean isShow = false;
    //用于保存拍照图片的uri
    private Uri mCameraUri;

    /**
     * 常规使用 通过意图进行跳转
     */
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    /**
     * 拍照活动结果启动器
     */
    private ActivityResultLauncher<Uri> takePictureActivityResultLauncher;
    /**
     * 相册活动结果启动器
     */
    private ActivityResultLauncher<String[]> openAlbumActivityResultLauncher;
    /**
     * 页面权限请求 结果启动器
     */
    private ActivityResultLauncher<String[]> permissionActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        //显示加载弹窗
        showLoading();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        initView();
    }

    /**
     * 注册
     */
    private void register() {
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                //从外部存储管理页面返回
                if (!isStorageManager()) {
                    showMsg("未打开外部存储管理开关，无法打开相册，抱歉");
                    return;
                }
                if (!hasPermission(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                    permissionActivityResultLauncher.launch(new String[]{PermissionUtils.READ_EXTERNAL_STORAGE});
                    //requestPermission(PermissionUtils.READ_EXTERNAL_STORAGE);
                    return;
                }
                //打开相册
                openAlbum();
            }
        });
        //调用MediaStore.ACTION_IMAGE_CAPTURE拍照，并将图片保存到给定的Uri地址，返回true表示保存成功。
        takePictureActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                modifyAvatar(mCameraUri.toString());
            }
        });
        // 提示用户选择文档，返回它的Uri。
        openAlbumActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
            modifyAvatar(result.toString());
        });
        //多个权限返回结果
        permissionActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            if (result.containsKey(PermissionUtils.CAMERA)) {
                //相机权限
                if (!result.get(PermissionUtils.CAMERA)) {
                    showMsg("您拒绝了相机权限，无法打开相机，抱歉。");
                    return;
                }
                takePicture();
            } else if (result.containsKey(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                //文件读写权限
                if (!result.get(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                    showMsg("您拒绝了读写文件权限，无法打开相册，抱歉。");
                    return;
                }
                openAlbum();
            } else if (result.containsKey(PermissionUtils.LOCATION)) {
                //定位权限
                if (!result.get(PermissionUtils.LOCATION)) {
                    showMsg("您拒绝了位置许可，将无法使用地图，抱歉。");
                }
            }
        });
    }

    /**
     * 初始化
     */
    private void initView() {
        //获取navController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.info_fragment:
                    binding.tvTitle.setText("热门资讯");
                    navController.navigate(R.id.info_fragment);
                    break;
                case R.id.map_fragment:
                    binding.tvTitle.setText("地图天气");
                    navController.navigate(R.id.map_fragment);
                    break;
                default:
            }
            return true;
        });
        binding.ivAvatar.setOnClickListener(v -> binding.drawerLayout.open());
        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_notebook://记事本
                    jumpActivity(NotebookActivity.class);
                    break;
                case R.id.item_setting:

                    break;
                case R.id.item_about:
                    jumpActivity(AboutActivity.class);
                    break;
                case R.id.item_logout:
                    logout();
                    break;
                default:
                    break;
            }
            return true;
        });
        //获取NavigationView的headerLayout视图
        View headerView = binding.navView.getHeaderView(0);
        headerView.setOnClickListener(v -> showModifyUserInfoDialog());
        //获取headerLayout视图的Binding
        NavHeaderBinding headerBinding = DataBindingUtil.bind(headerView);
        //获取本地用户信息
        homeViewModel.getUser();
        //用户信息发生改变时给对应的xml设置数据源也就是之前写好的ViewModel。
        homeViewModel.user.observe(this, user -> {
            localUser = user;
            binding.setHomeViewModel(homeViewModel);
            if (headerBinding != null) {
                headerBinding.setHomeViewModel(homeViewModel);
            }
            //隐藏加载弹窗
            dismissLoading();
        });
        //请求定位权限
        requestLocation();
    }

    /**
     * 请求定位权限
     */
    private void requestLocation() {
        if (!isAndroid6()) {
            showMsg("您无需动态请求权限");
            return;
        }
        if (!hasPermission(PermissionUtils.LOCATION)) {
            //请求位置权限
            permissionActivityResultLauncher.launch(new String[]{PermissionUtils.LOCATION});
        }
    }

    /**
     * 显示修改用户弹窗
     */
    private void showModifyUserInfoDialog() {
        DialogModifyUserInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_modify_user_info, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .addDefaultAnimation()
                .setCancelable(true)
                .setContentView(binding.getRoot())
                .setWidthAndHeight(SizeUtils.dp2px(this, 300), LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(R.id.tv_modify_avatar, v -> {
                    //修改头像，点击显示修改头像的方式，再次点击隐藏修改方式
                    binding.layModifyAvatar.setVisibility(isShow ? View.GONE : View.VISIBLE);
                    isShow = !isShow;
                }).setOnClickListener(R.id.tv_album_selection, v -> albumSelection())//相册选择
                .setOnClickListener(R.id.tv_camera_photo, v -> cameraPhoto())//相机拍照
                .setOnClickListener(R.id.tv_modify_nickname, v -> showEditDialog(0))//修改昵称
                .setOnClickListener(R.id.tv_modify_Introduction, v -> showEditDialog(1))//修改简介
                .setOnClickListener(R.id.tv_close, v -> modifyUserInfoDialog.dismiss())//关闭弹窗
                .setOnDismissListener(dialog -> isShow = false);
        modifyUserInfoDialog = builder.create();
        modifyUserInfoDialog.show();
    }

    /**
     * 显示可输入文字弹窗
     *
     * @param type 0 修改昵称  1  修改简介
     */
    private void showEditDialog(int type) {
        modifyUserInfoDialog.dismiss();
        DialogEditBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_edit, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .addDefaultAnimation()
                .setCancelable(true)
                .setText(R.id.tv_title, type == 0 ? "修改昵称" : "修改简介")
                .setContentView(binding.getRoot())
                .setWidthAndHeight(SizeUtils.dp2px(this, 300), LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(R.id.tv_cancel, v -> editDialog.dismiss())
                .setOnClickListener(R.id.tv_sure, v -> {
                    String content = binding.etContent.getText().toString().trim();
                    if (content.isEmpty()) {
                        showMsg(type == 0 ? "请输入昵称" : "请输入简介");
                        return;
                    }
                    if (type == 0 && content.length() > 10) {
                        showMsg("昵称过长，请输入8个以内汉字或字母");
                        return;
                    }
                    editDialog.dismiss();
                    showLoading();
                    //保存输入的值
                    modifyContent(type, content);
                });
        editDialog = builder.create();
        binding.etContent.setHint(type == 0 ? "请输入昵称" : "请输入简介");
        editDialog.show();
    }

    /**
     * 修改内容
     *
     * @param type    类型 0：昵称 1：简介 2: 头像
     * @param content 修改内容
     */
    private void modifyContent(int type, String content) {
        if (type == 0) {
            localUser.setNickname(content);
        } else if (type == 1) {
            localUser.setIntroduction(content);
        } else if (type == 2) {
            localUser.setAvatar(content);
        }
        homeViewModel.updateUser(localUser);
        homeViewModel.failed.observe(this, failed -> {
            dismissLoading();
            if ("200".equals(failed)) {
                showMsg("修改成功");
            }
        });
    }

    /**
     * 相册拍照
     */
    private void cameraPhoto() {
        modifyUserInfoDialog.dismiss();
        if (!isAndroid6()) {
            //打开相机
            takePicture();
            return;
        }
        if (!hasPermission(PermissionUtils.CAMERA)) {
            //请求相机权限
            permissionActivityResultLauncher.launch(new String[]{PermissionUtils.CAMERA});
            return;
        }
        //打开相机
        takePicture();
    }

    /**
     * 新的拍照
     */
    private void takePicture() {
        mCameraUri = getContentResolver().insert(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        takePictureActivityResultLauncher.launch(mCameraUri);
    }

    /**
     * 相册选择
     */
    private void albumSelection() {
        modifyUserInfoDialog.dismiss();
        if (isAndroid11()) {
            //请求打开外部存储管理
            requestManageExternalStorage(intentActivityResultLauncher);
        } else {
            if (!isAndroid6()) {
                //打开相册
                openAlbum();
                return;
            }
            if (!hasPermission(PermissionUtils.READ_EXTERNAL_STORAGE)) {
                //请求文件存储权限
                permissionActivityResultLauncher.launch(new String[]{PermissionUtils.READ_EXTERNAL_STORAGE});
                return;
            }
            //打开相册
            openAlbum();
        }
    }

    /**
     * 打开相册 采用新的方式
     */
    private void openAlbum() {
        openAlbumActivityResultLauncher.launch(new String[]{"image/*"});
    }

    /**
     * 修改头像
     */
    private void modifyAvatar(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //保存到数据表中
            modifyContent(2, imagePath);
            Log.d(TAG, "modifyAvatar: " + imagePath);
        } else {
            showMsg("图片获取失败");
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        showMsg("退出登录");
        mvUtils.put(Constant.IS_LOGIN, false);
        jumpActivityFinish(LoginActivity.class);
    }
}