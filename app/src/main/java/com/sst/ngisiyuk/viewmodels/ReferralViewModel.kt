package com.sst.ngisiyuk.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sst.ngisiyuk.models.ngisiyuk.GetProfil
import com.sst.ngisiyuk.services.NgisiyukServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReferralViewModel @Inject constructor(
    private val api : NgisiyukServices,
    private val id :String
): ViewModel() {

    val responseUpdateParent = MutableLiveData<GetProfil>()


    fun updateParent(kode: String) {
        viewModelScope.launch {
            val response = api.updateParent(id, kode)

            if (response.isSuccessful) responseUpdateParent.value = response.body()
        }
    }
}