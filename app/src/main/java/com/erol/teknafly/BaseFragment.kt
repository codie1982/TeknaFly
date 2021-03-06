package com.erol.teknafly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment:Fragment() {

    @Inject
    lateinit var viewModelFactoryProvider: ViewModelProvider.Factory
}