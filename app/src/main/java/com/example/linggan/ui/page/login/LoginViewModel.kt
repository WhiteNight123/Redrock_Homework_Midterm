package com.example.linggan.ui.page.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linggan.logic.Repository
import com.example.linggan.logic.model.BaseData
import com.example.linggan.logic.model.LoginData
import com.example.linggan.ui.Nav.LingGanDestination
import kotlinx.coroutines.launch

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/3
 */
class LoginViewModel : ViewModel() {

    val message = MutableLiveData<BaseData<LoginData>>()
    fun setToken(phone: String) {
        viewModelScope.launch {
//            val token = stringPreferencesKey("login_token")
            Log.e("TAG", "setToken: ${LingGanDestination.TOKEN}")
            val a = Repository.login(phone)
            LingGanDestination.TOKEN = "Bearer ${a.data.token}"
            message.postValue(a)
            Log.e("TAG", "setToken: ${LingGanDestination.TOKEN}")
//            context.dataStore.edit { str->
//                str[token]="Bearer ${Repository.login(phone).data.token}"
//                LingGanDestination.TOKEN= str[token]!!
//            }
        }

    }
}