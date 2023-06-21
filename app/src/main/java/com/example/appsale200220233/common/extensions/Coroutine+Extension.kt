package com.example.appsale200220233.common.extensions

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by pphat on 6/19/2023.
 */

fun launchOnBackground(
    block: suspend CoroutineScope.() -> Unit
) = CoroutineScope(Dispatchers.IO).launch(
    start = CoroutineStart.DEFAULT,
    block = block
)

fun launchOnMain(
    block: suspend CoroutineScope.() -> Unit
) = CoroutineScope(Dispatchers.Main).launch(
    start = CoroutineStart.DEFAULT,
    block = block
)
