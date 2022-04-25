package com.zyc.lib.widget.dotloading

import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope

@Stable
interface DotState {
    fun start(scope: CoroutineScope)
    operator fun get(index: Int): Float
}