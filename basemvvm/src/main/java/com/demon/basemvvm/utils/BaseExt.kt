package com.demon.basemvvm.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType


/**
 * @author DeMon
 * Created on 2020/11/25.
 * E-mail 757454343@qq.com
 * Desc:
 */
var mmkv = MMKV.defaultMMKV()

/**
 * 获取当前类的TAG
 */
inline val <T : Any> T.Tag: String
    get() = this.javaClass.simpleName


/**
 * 获取泛型类的Class类型
 * @param index,表示第几个泛型
 */
inline fun <T : Any> Any.getTClass(index: Int = 0): Class<T> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>

/**
 * 反射执行ViewBinding的inflate静态方法，主要在Activity中使用
 *
 * @param inflater LayoutInflater参数
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(inflater: LayoutInflater, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VB
}

/**
 * 反射执行ViewBinding的inflate静态方法，主要在Fragment中使用
 *
 * @param inflater LayoutInflater参数
 * @param container ViewGroup
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, inflater, container, false) as VB
}

/**
 * 反射执行ViewBinding的inflate静态方法,主要在DataViewHolder中使用
 * @param container ViewGroup
 * @param index 表示第几个泛型
 */
inline fun <VB : ViewBinding> Any.inflateViewBinding(container: ViewGroup, index: Int = 0): VB {
    val cla = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<VB>
    return cla.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, LayoutInflater.from(container.context), container, false) as VB
}


/**
 * io线程
 */
fun CoroutineScope.launchIO(block: suspend () -> Unit): Job {
    return this.launch(Dispatchers.IO) { block() }
}

/**
 * 主线程
 */
fun CoroutineScope.launchUI(block: suspend () -> Unit): Job {
    return this.launch(Dispatchers.Main) { block() }
}