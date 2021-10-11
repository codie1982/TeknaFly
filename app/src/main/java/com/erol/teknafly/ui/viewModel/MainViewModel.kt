package com.erol.teknafly.ui.viewModel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.erol.teknafly.data.model.Position
import com.erol.teknafly.data.model.Satellite
import com.erol.teknafly.data.source.repository.HomeRepositoryImpl
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private lateinit var context: Context
    private var repository: HomeRepositoryImpl = HomeRepositoryImpl()
    var satellites: MutableLiveData<MutableList<Satellite>> = MutableLiveData()
    var selectedSatellite: MutableLiveData<Satellite>  =  MutableLiveData()
    var satellitePosition: MutableLiveData<Position> =  MutableLiveData()
    val _isLoading = MutableLiveData<Boolean>()

    private lateinit var nTimer: Timer
    var selectedPositionId = 0
    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun getSatellites() {
        viewModelScope.launch {
            satellites.value =repository.getSatellites(context, false)
        }
    }
    fun getSatelliteDetail(id: Int) {
        viewModelScope.launch {
            selectedSatellite.value =repository.getSatellitesDetail(context, id)
        }
    }
    fun getSatellitePosition(satelliteid:Int,positionId:Int) {
        viewModelScope.launch {
            var nPosition = repository.getSatellitesPosition(context, satelliteid,positionId)
            satellitePosition.value = nPosition
        }
    }

    fun setLastPositionTimer(selectedId:Int){
        nTimer = Timer()
        nTimer.schedule(object :TimerTask(){
            override fun run() {
                if(selectedPositionId > 2){
                    selectedPositionId=0
                }
                getSatellitePosition(selectedId,selectedPositionId)
                selectedPositionId ++
            }

        },0,1000)
    }
    fun stopGetLastPosition(){
        nTimer.cancel()
    }
}


