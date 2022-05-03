package com.example.linggan.ui.page.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.linggan.LingGanApp.Companion.context
import com.example.linggan.ui.Nav.LingGanDestination
import com.example.linggan.utils.showToast

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun Login(modifier: Modifier) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginPhone = remember {
        mutableStateOf("")
    }
    val keyboard = LocalSoftwareKeyboardController.current
    val message = loginViewModel.message.observeAsState()
    if (LingGanDestination.TOKEN == "") {
        Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "推荐账号: 13131306201")
            OutlinedTextField(
                colors = textFieldColors(
                    focusedIndicatorColor = Color.Blue,
                    focusedLabelColor = Color.Blue
                ),
                value = loginPhone.value,
                onValueChange = { loginPhone.value = it },
                label = {
                    Text(text = "请输入手机号")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    loginViewModel.setToken(loginPhone.value)
                    keyboard?.hide()
                })
            )
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    loginViewModel.setToken(loginPhone.value)

                    keyboard?.hide()
                }) {
                Text(text = "登录")
            }
            message.value?.let { Text(text = it.message, modifier = Modifier.offset(y = 100.dp)) }
        }
    } else {
        showToast(context, "您已登录")
    }
}