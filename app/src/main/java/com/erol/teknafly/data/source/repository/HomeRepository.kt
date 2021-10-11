package com.erol.teknafly.data.source.repository

import android.content.Context
import com.erol.teknafly.data.model.Position
import com.erol.teknafly.data.model.Satellite

interface HomeRepository {
    suspend fun getSatellites(context: Context,detail:Boolean): MutableList<Satellite>
    suspend fun getSatellitesDetail(context: Context,selectedId:Int): Satellite?
    suspend fun getSatellitesPosition(context: Context,satelliteid:Int,positionId:Int): Position?

}
