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
    lateinit var satellites: MutableLiveData<MutableList<Satellite>>
    lateinit var selectedSatellite: MutableLiveData<Satellite>
    lateinit var satellitePosition: MutableLiveData<Position>
    val _isLoading = MutableLiveData<Boolean>()

    val _seconds = MutableLiveData<Int>()
    private lateinit var timer: CountDownTimer
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
            satellites = MutableLiveData(repository.getSatellites(context, false))
        }
    }
    fun getSatelliteDetail(id: Int) {
        viewModelScope.launch {
            selectedSatellite = MutableLiveData(repository.getSatellitesDetail(context, id))
        }
    }
    fun getSatellitePosition(satelliteid:Int,positionId:Int) {
        viewModelScope.launch {
            satellitePosition = MutableLiveData(repository.getSatellitesPosition(context, satelliteid,positionId))
        }
    }

    fun getLastPositionTimer(selectedId:Int){
        nTimer = Timer()
        nTimer.scheduleAtFixedRate(object :TimerTask(){
            override fun run() {
                if(selectedPositionId > 2){
                    selectedPositionId=0
                }
                selectedPositionId ++
                getSatellitePosition(selectedId,selectedPositionId)
            }

        },0,1000)
    }
    fun stopGetLastPosition(){
        nTimer.cancel()
    }
}


