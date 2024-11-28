package com.example.samparcel.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samparcel.app.models.Parcel
import com.samparcel.app.repositories.ParcelRepository
import com.samparcel.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParcelViewModel @Inject constructor(
    private val parcelRepository: ParcelRepository
) : ViewModel() {
    private val _parcelCreationState = MutableStateFlow<Resource<Parcel>>(Resource.Idle())
    val parcelCreationState: StateFlow<Resource<Parcel>> = _parcelCreationState

    private val _parcelTrackingState = MutableStateFlow<Resource<Parcel>>(Resource.Idle())
    val parcelTrackingState: StateFlow<Resource<Parcel>> = _parcelTrackingState

    private val _userParcelsState = MutableStateFlow<Resource<List<Parcel>>>(Resource.Idle())
    val userParcelsState: StateFlow<Resource<List<Parcel>>> = _userParcelsState

    fun createParcel(parcel: Parcel) {
        viewModelScope.launch {
            parcelRepository.createParcel(parcel).collect { result ->
                _parcelCreationState.value = result
            }
        }
    }

    fun trackParcel(trackingNumber: String) {
        viewModelScope.launch {
            parcelRepository.trackParcel(trackingNumber).collect { result ->
                _parcelTrackingState.value = result
            }
        }
    }

    fun getUserParcels(userId: String) {
        viewModelScope.launch {
            parcelRepository.getUserParcels(userId).collect { result ->
                _userParcelsState.value = result
            }
        }
    }
}