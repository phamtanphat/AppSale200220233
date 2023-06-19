package com.example.appsale200220233.common.extensions

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by pphat on 6/19/2023.
 */

fun CoroutineScope.launchOnBackground(
    handleException: (CoroutineContext, Throwable) -> Unit,
    block: suspend CoroutineScope.() -> Unit
) = launch(
    Dispatchers.IO + CoroutineExceptionHandler(handleException),
    start = CoroutineStart.DEFAULT,
    block = block
)

fun CoroutineScope.launchOnMain(block: suspend CoroutineScope.() -> Unit) = launch(
    Dispatchers.Main,
    start = CoroutineStart.DEFAULT,
    block = block
)
