package com.sst.ngisiyuk.viewmodels

import androidx.lifecycle.ViewModel
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    val api :NgisiyukServices,
): ViewModel()