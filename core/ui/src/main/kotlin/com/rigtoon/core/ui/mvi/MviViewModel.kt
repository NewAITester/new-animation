package com.rigtoon.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * پایهٔ MVI با وضعیت فقط‌خواندنی و پردازش ترتیبی رویدادها.
 *
 * هر صفحه باید فقط یک مصرف‌کنندهٔ فعال برای [effects] داشته باشد.
 * خطاهای مورد انتظار دامنه باید در [handleAction] به وضعیت یا اثر تبدیل شوند.
 */
abstract class MviViewModel<State : Any, Action : Any, Effect : Any>(
    initialState: State,
) : ViewModel() {
    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    private val actionChannel: Channel<Action> = Channel(Channel.BUFFERED)
    private val effectChannel: Channel<Effect> = Channel(Channel.BUFFERED)

    val state: StateFlow<State> = mutableState.asStateFlow()
    val effects: Flow<Effect> = effectChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            for (action in actionChannel) {
                handleAction(action)
            }
        }
    }

    /** رویداد را بدون مسدودکردن رشتهٔ رابط به صف اضافه می‌کند. */
    fun onAction(action: Action): Unit {
        viewModelScope.launch {
            actionChannel.send(action)
        }
    }

    /** تابع تغییر وضعیت باید بدون عارضهٔ جانبی باشد. */
    protected fun updateState(transform: (State) -> State): Unit {
        mutableState.update(transform)
    }

    protected suspend fun emitEffect(effect: Effect): Unit {
        effectChannel.send(effect)
    }

    protected abstract suspend fun handleAction(action: Action): Unit

    protected override fun onCleared(): Unit {
        actionChannel.cancel()
        effectChannel.cancel()
        super.onCleared()
    }
}
