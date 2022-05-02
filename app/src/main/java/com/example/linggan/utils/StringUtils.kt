package com.example.linggan.utils

import androidx.compose.ui.graphics.Color

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */


private val SEP1: String = "#"
private val SEP2 = "|"
private val SEP3 = "="

/**
 * List转换String
 *
 * @param list
 * :需要转换的List
 * @return String转换后的字符串
 */
fun ListToString(list: List<*>?): String {
    val sb = StringBuffer()
    if (list != null && list.size > 0) {
        for (i in list.indices) {
            if (list[i] == null || list[i] === "") {
                continue
            }
            // 如果值是list类型则调用自己
            if (list[i] is List<*>) {
                sb.append(ListToString(list[i] as List<*>?))
                sb.append(SEP1)
            } else if (list[i] is Map<*, *>) {
                sb.append(MapToString(list[i] as Map<*, *>))
                sb.append(SEP1)
            } else {
                sb.append(list[i])
                sb.append(SEP1)
            }
        }
    }
    return "L$sb"
}

/**
 * Map转换String
 *
 * @param map
 * :需要转换的Map
 * @return String转换后的字符串
 */
fun MapToString(map: Map<*, *>): String {
    val sb = StringBuffer()
    // 遍历map
    for (obj: Any? in map.keys) {
        if (obj == null) {
            continue
        }
        val key: Any = obj
        val value = map[key]
        if (value is List<*>) {
            sb.append(key.toString() + SEP1 + ListToString(value as List<*>?))
            sb.append(SEP2)
        } else if (value is Map<*, *>) {
            sb.append(
                key.toString() + SEP1
                        + MapToString(value)
            )
            sb.append(SEP2)
        } else {
            sb.append(key.toString() + SEP3 + value.toString())
            sb.append(SEP2)
        }
    }
    return "M$sb"
}

/**
 * String转换Map
 *
 * @param mapText
 * :需要转换的字符串
 * @param KeySeparator
 * :字符串中的分隔符每一个key与value中的分割
 * @param ElementSeparator
 * :字符串中每个元素的分割
 * @return Map
 */
fun StringToMap(mapText: String?): Map<String, Any?>? {
    var mapText = mapText
    if (mapText == null || (mapText == "")) {
        return null
    }
    mapText = mapText.substring(1)
    mapText = mapText
    val map: MutableMap<String, Any?> = HashMap()
    val text = mapText.split("\\" + SEP2).toTypedArray() // 转换为数组
    for (str: String in text) {
        val keyText = str.split(SEP3).toTypedArray() // 转换key与value的数组
        if (keyText.size < 1) {
            continue
        }
        val key = keyText[0] // key
        val value = keyText[1] // value
        if (value[0] == 'M') {
            val map1: Map<*, *>? = StringToMap(value)
            map[key] = map1
        } else if (value[0] == 'L') {
            val list = StringToList(value)
            map[key] = list
        } else {
            map[key] = value
        }
    }
    return map
}

/**
 * String转换List
 *
 * @param listText
 * :需要转换的文本
 * @return List
 */
fun StringToList(listText: String?): List<Any?>? {
    var listText = listText
    if (listText == null || (listText == "")) {
        return null
    }
    listText = listText.substring(1)
    listText = listText
    val list: MutableList<Any?> = ArrayList()
    val text = listText.split((SEP1)).toTypedArray()
    for (str: String in text) {
        if (str[0] == 'M') {
            val map: Map<*, *>? = StringToMap(str)
            list.add(map)
        } else if (str[0] == 'L') {
            val lists = StringToList(str)
            list.add(lists)
        } else {
            list.add(str)
        }
    }
    return list
}

fun listToString(list: List<*>): String {
    //把List集合转换为字符串用&隔开
    //把List集合转换为字符串用&隔开
    val stringBuffer = StringBuffer()
    for (element in list) {
        stringBuffer.append(element.toString().trim() + "&")
    }
    val s = stringBuffer.substring(0, stringBuffer.length - 1).toString()
    return s
}
fun stringToList(str:String):List<Any>{

    val list: ArrayList<String> = ArrayList()
    val split: List<String> = str.split("&")
    for (i in split.indices) {
        list.add(split[i])
    }
    return list
}