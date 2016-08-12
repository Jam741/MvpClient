package com.yingwumeijia.android.funcation.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rx.android.jamspeedlibrary.utils.GlideCircleTransform;
import com.rx.android.jamspeedlibrary.utils.LogUtil;
import com.rx.android.jamspeedlibrary.utils.T;
import com.rx.android.jamspeedlibrary.utils.TextViewUtils;
import com.yingwumeijia.android.R;
import com.yingwumeijia.android.WorkerApp;
import com.yingwumeijia.android.data.bean.BaseBean;
import com.yingwumeijia.android.utils.base.activity.BaseActivity;
import com.yingwumeijia.android.utils.constants.Constant;
import com.yingwumeijia.android.utils.net.GlideUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Jam on 16/6/15 下午7:19.
 * Describe:个人信息页面
 */
public class PersonInfoActivity extends BaseActivity {

    @Bind(R.id.topTitle)
    TextView topTitle;
    @Bind(R.id.topLeft)
    TextView topLeft;
    @Bind(R.id.topLeft_second)
    TextView topLeftSecond;
    @Bind(R.id.topRight)
    TextView topRight;
    @Bind(R.id.iv_portrait)
    ImageView ivPortrait;
    @Bind(R.id.btn_portrait)
    LinearLayout btnPortrait;
    @Bind(R.id.tv_nkname)
    TextView tvNkname;
    @Bind(R.id.btn_nkname)
    LinearLayout btnNkname;
    @Bind(R.id.tv_mob)
    TextView tvMob;
    @Bind(R.id.btn_mob)
    LinearLayout btnMob;


    private CameraSdkParameterInfo mCameraSdkParameterInfo = new CameraSdkParameterInfo();

    public static final int request_code_portrait = CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY;
    public static final int request_code_nickkname = 1;
    public static final int request_code_mob = 2;
    public static final int request_code_READ_EXTERNAL_STORAGE = 001;
    public static final String KEY_PORTRAIT = "KEY_PORTRAIT";
    public static final String KEY_NIKENAME = "KEY_NIKENAME";
    public static final String KEY_MOB = "KEY_MOB";

    public String portrait;
    public String nikeName;
    public String mob;


    public static void start(Activity activity, String portrait, String nikeName, String mob) {
        Intent intent = new Intent(activity, PersonInfoActivity.class);
        intent.putExtra(KEY_PORTRAIT, portrait);
        intent.putExtra(KEY_NIKENAME, nikeName);
        intent.putExtra(KEY_MOB, mob);
        ActivityCompat.startActivity(activity, intent, Bundle.EMPTY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.persion_info_act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        initActionBar();
        initData();
        initView();
    }

    private void initView() {
        if (portrait == null || portrait.length() == 0) {
            GlideUtils.loadCriclePortraitImage(context, ivPortrait, R.mipmap.mine_user_ywmj_circle);
        } else {
            GlideUtils.loadCriclePortraitImage(context, ivPortrait, portrait);
        }
        tvNkname.setText(nikeName);
        tvMob.setText(mob);
    }

    private void initData() {
        portrait = getIntent().getStringExtra(KEY_PORTRAIT);
        nikeName = getIntent().getStringExtra(KEY_NIKENAME);
        mob = getIntent().getStringExtra(KEY_MOB);
    }

    private void initActionBar() {
        TextViewUtils.setDrawableToLeft(context, topLeft, R.mipmap.back_ico);
        topTitle.setText("个人信息");
    }

    @OnClick({R.id.topLeft, R.id.btn_portrait, R.id.btn_nkname, R.id.btn_mob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topLeft:
                ActivityCompat.finishAfterTransition(context);
                break;
            case R.id.btn_portrait:
                //选择头像
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code_READ_EXTERNAL_STORAGE);
                } else {
                    editPortrait();
                }
                break;
            case R.id.btn_nkname:
//                editNkName();
                break;
            case R.id.btn_mob:
//                editMob();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case request_code_READ_EXTERNAL_STORAGE:
                doNext(requestCode, grantResults);
                break;
        }
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == request_code_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                editPortrait();
            } else {
                // Permission Denied
                T.showShort(context, R.string.no_pressmion);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && resultCode == RESULT_OK) {
            switch (requestCode) {
                case request_code_mob:
                    String mob = data.getStringExtra("KEY_INPUT_RESULT");
                    Timber.d(mob);
                    break;
                case request_code_nickkname:
                    String nikeName = data.getStringExtra("KEY_INPUT_RESULT");
                    LogUtil.getInstance().debug(nikeName);
                    break;
                case request_code_portrait:
                    Bundle b = data.getExtras();
                    getBundle(b);
                    break;
            }
        }
    }

    private void getBundle(Bundle b) {
        if (b != null) {
            mCameraSdkParameterInfo = (CameraSdkParameterInfo) b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            ArrayList<String> list = mCameraSdkParameterInfo.getImage_list();
            LogUtil.getInstance().debug("partraot", list.get(0));
            Glide.with(context).load(Uri.fromFile(new File(list.get(0)))).transform(new GlideCircleTransform(context)).into(ivPortrait);
            String key = new File(list.get(0)).getName();
            LogUtil.getInstance().debug("jam", "======key=========" + key);
            upLoadPortrait(list.get(0), key);
        }
    }

    private void upLoadPortrait(final String filePath, final String key) {
        showBaseProgresDialog();
        WorkerApp
                .getApiService()
                .getUpLoadToken()
                .enqueue(new Callback<BaseBean<String>>() {
                    @Override
                    public void onResponse(Call<BaseBean<String>> call, Response<BaseBean<String>> response) {
                        LogUtil.getInstance().debug("===============" + response.body().getData());
                        LogUtil.getInstance().debug("===============" + response.body().getSucc());
                        LogUtil.getInstance().debug("===============" + response.body().getMessage());
                        if (response.body().getSucc()) {
                            UploadManager uploadManager = new UploadManager();

                            uploadManager.put(filePath, key, response.body().getData(), new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject res) {
                                    //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                    Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    LogUtil.getInstance().debug("=======picUrl========" + Constant.BASE_QINIU_URL + key);
                                    postToSever(Constant.BASE_QINIU_URL + key);
                                }
                            }, null);
                        } else {
                            dismisBaseProgressDialog();
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseBean<String>> call, Throwable t) {

                    }
                });
    }


    private void postToSever(String url) {
        WorkerApp.getApiService()
                .uploadHeadImage(url)
                .enqueue(new Callback<BaseBean>() {
                    @Override
                    public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                        dismisBaseProgressDialog();
                        if (response.body().getSucc()) {
                            T.showShort(context, "头像上传成功");
                        } else {
                            T.showShort(context, "头像上传失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseBean> call, Throwable t) {
                        dismisBaseProgressDialog();
                        showBaseNetConnectError();
                    }
                });
    }


    /**
     * edit mobile phone number
     */
    private void editMob() {
        EditPersionInfoActivity.start(context, EditPersionInfoActivity.EDIT_TYPE.MOB, request_code_mob, mob);
    }

    /**
     * edit nickname
     */
    private void editNkName() {
        EditPersionInfoActivity.start(context, EditPersionInfoActivity.EDIT_TYPE.NICKNAME, request_code_nickkname, nikeName);
    }

    /**
     * edit portrait
     */
    private void editPortrait() {
        Timber.d("showCamer====");
        mCameraSdkParameterInfo.setFilter_image(false);
        mCameraSdkParameterInfo.setShow_camera(true);
        mCameraSdkParameterInfo.setSingle_mode(true);
        mCameraSdkParameterInfo.setCroper_image(true);
        Intent intent = new Intent();
        intent.setClassName(getApplication(), "com.muzhi.camerasdk.PhotoPickActivity");
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
        intent.putExtras(b);
        ActivityCompat.startActivityForResult(context, intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY, null);
    }

}
