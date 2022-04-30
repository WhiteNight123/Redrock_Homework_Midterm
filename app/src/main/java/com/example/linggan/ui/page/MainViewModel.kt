package com.example.linggan.ui.page

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.linggan.ui.Nav.Tabs

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _position = MutableLiveData(Tabs.SPECTRUM_PAGE)
    val position: LiveData<Tabs> = _position
    fun onPositionChanged(position: Tabs) {
        _position.value = position
    }
}