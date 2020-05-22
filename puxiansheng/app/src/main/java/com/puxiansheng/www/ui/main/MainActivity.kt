package com.puxiansheng.www.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.puxiansheng.logic.api.API
import com.puxiansheng.logic.bean.User
import com.puxiansheng.util.ext.SharedPreferencesUtil.Companion.get
import com.puxiansheng.www.R
import com.puxiansheng.www.app.MyBaseActivity
import com.puxiansheng.www.common.LiveDataBus
import com.puxiansheng.www.common.urlIcon
import com.puxiansheng.www.login.WechatAPI
import com.puxiansheng.www.ui.main.dialog.UpgradeDialog
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : MyBaseActivity() {
    var context: Context = this@MainActivity
    private val requestCodePermissions = 10
    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var appModel: MainViewModel? = null
    private var isUpDialogShow: Boolean = false
    private var currentTokenCode: Int = 0

    override fun getLayoutId(): Int {
       return R.layout.activity_home
    }

    override fun business() {
        appModel = ViewModelProvider(this)[MainViewModel::class.java]
        ActivityCompat.requestPermissions(
            this,
            requiredPermissions,
            requestCodePermissions
        )
        var isLogin = false

        appModel?.saveLoginUser()?.let {
            appModel?.currentUser?.postValue(it)
            API.setAuthToken(it.token)
        }

        LiveDataBus.get().with("user", User::class.java)
            ?.observe(this, Observer { user ->
                user?.let {
                    appModel?.currentUser?.postValue(user)
                }
            })

        appModel?.currentSignatureToken?.observe(this@MainActivity, Observer {
            it?.let {
                appModel?.getSelectiveMenuDataFromRemote()
                appModel?.getSystemConfigFromRemote()

                if (appModel?.currentCity?.value == null)
                    appModel?.getCurrentLocation()

                if (!isLogin) lifecycleScope.launch(Dispatchers.IO) {
                    get(API.LOGIN_USER_TOKEN, "")?.let { it ->
                        appModel?.currentUser?.postValue(appModel?.saveLoginUser())
                        API.setAuthToken(it.toString())
                    }
                }
            }
        })

        appModel?.currentNewPackage?.observe(this@MainActivity, Observer {
            it?.let {
                when (currentTokenCode) {
                    API.CODE_SUCCESS -> {
                        if (!isUpDialogShow && it.newVersion == 1) {
                            UpgradeDialog(
                                this@MainActivity,
                                onClick = {},
                                versionName = it.showVersion,
                                fileDownUrl = it.downloadUrl,
                                upgadeTips = it.tipsMsg,
                                upgradeType = it.newPackage, isForceUpgrade = false
                            ).show(
                                supportFragmentManager,
                                UpgradeDialog::class.java.name
                            )
                            isUpDialogShow = true
                        }
                    }

                    API.CODE_BANNED_VERSION -> {
                        if (!isUpDialogShow && it.newVersion == 1) {
                            UpgradeDialog(
                                this@MainActivity,
                                onClick = {
                                    finish()
                                },
                                versionName = it.showVersion,
                                fileDownUrl = it.downloadUrl,
                                upgadeTips = it.tipsMsg,
                                upgradeType = it.newPackage, isForceUpgrade = true
                            ).show(
                                supportFragmentManager,
                                UpgradeDialog::class.java.name
                            )
                            isUpDialogShow = true
                        }
                    }
                }
            }
        })


        appModel?.currentSignatureTokenCode?.observe(this@MainActivity, Observer { code ->
            currentTokenCode = code
        })

        appModel?.requireLocalDevice()?.observe(this@MainActivity, Observer {
            it?.let { appModel?.refreshSignatureToken(it) } ?: appModel?.requireDevice()
        })

        API.logoutSignal.observe(this@MainActivity, Observer {
            if (it == API.CODE_REQUIRE_LOGIN ||
                it == API.CODE_AUTO_CODE_INVALID ||
                it == API.CODE_AUTO_CODE_EXPIRED ||
                it == API.CODE_AUTO_CODE_ERROR ||
                it == API.CODE_AUTO_CODE_EMPTY ||
                it == API.CODE_BANNED_USER
            ) {
                appModel?.forceLogout()
                Toast.makeText(this, "当前登录已失效，请重新登录！", Toast.LENGTH_SHORT).show()
            }
        })
        applicationContext?.let {
            WechatAPI.instance = WXAPIFactory.createWXAPI(it, "wxe5266f2fb1236eee", true)
        }
    }




    override fun onDestroy() {
        appModel = null
        super.onDestroy()
    }




}