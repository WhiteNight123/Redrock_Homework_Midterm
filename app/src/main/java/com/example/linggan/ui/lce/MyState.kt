package com.example.linggan.ui.lce

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */
sealed class MyState<out R> {
    fun isLoading() = this is MyLoading
    fun isSuccessful() = this is MySuccess
    override fun toString(): String {
        return when (this) {
            is MySuccess<*> -> "Success[data=$data]"
            is MyError -> "Error[exception=${error}"
            is MyNoContent -> "No Data reason=$reason"
            MyLoading -> "Loading"
        }
    }
}

data class MySuccess<out T>(val data: T) : MyState<T>()
data class MyError(val error: Throwable) : MyState<Nothing>()
data class MyNoContent(val reason: String) : MyState<Nothing>()
object MyLoading : MyState<Nothing>()