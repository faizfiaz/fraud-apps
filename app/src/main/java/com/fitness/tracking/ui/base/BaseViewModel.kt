package com.fitness.tracking.ui.base

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitness.tracking.utils.AndroidUtils
import com.fitness.tracking.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<U, N : BaseNavigator?>(baseUsecase: U, schedulerProvider: SchedulerProvider) : ViewModel() {
    private var mNavigator: WeakReference<N>? = null
    protected val baseUsecase: U
    var compositeDisposable: CompositeDisposable
        protected set
    val schedulerProvider: SchedulerProvider

    var appBarTitle = ObservableField<String>()
    var showProgressBar = MutableLiveData<Boolean>(false)
    var showAlertDialog = ObservableBoolean(false)
    var showHistory = ObservableBoolean(false)
    var showAddButton = ObservableBoolean(false)
    var showAppBar = ObservableBoolean(true)
    var progressBarMessage = ObservableField<String?>()

    abstract fun defineLayout()
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val navigator: N?
        get() {
            return mNavigator!!.get()
        }


    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }

    fun isLoading(isLoading: Boolean) {
        showProgressBar.postValue(isLoading)
    }

    fun isLoading(isLoading: Boolean, message: Int) {
        showProgressBar.postValue(isLoading)
        progressBarMessage.set(AndroidUtils.getString(message))
    }

    protected fun onError(throwable: Throwable) {
        isLoading(false)
        navigator?.handleError(throwable)
        showAlertDialog.set(true)
        throwable.printStackTrace()
    }

    abstract fun onSuccess(o: Any?)

    open fun dispose() {

    }

    init {
        compositeDisposable = CompositeDisposable()
        this.schedulerProvider = schedulerProvider
        this.baseUsecase = baseUsecase
        defineLayout()
    }
}