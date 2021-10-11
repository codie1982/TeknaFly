package com.erol.teknafly.data.source.repository

import android.content.Context
import com.erol.teknafly.data.model.*
import com.erol.teknafly.data.util.ReadFile
import com.erol.teknafly.data.util.fromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeRepositoryImpl : HomeRepository {

    // Uydu Listesi
    override suspend fun getSatellites(context: Context,detail:Boolean): MutableList<Satellite> {
        var readFie = ReadFile(context)
        var json :Any
        if(detail){
             json = readFie.readFileFromJson("satellite-detail.json")!!
        }else{
             json = readFie.readFileFromJson("satellite-list.json")!!
        }
        if(json !=null)
            return Gson().fromJson(json)
        else
            return emptyList<Satellite>() as MutableList<Satellite>
    }

    // Uydu Detay Listesi
    override suspend fun getSatellitesDetail(context: Context,selectedId:Int): Satellite? {
        var readFie = ReadFile(context)
        var jsonList = readFie.readFileFromJson("satellite-list.json")!!
        var jsonListDetail = readFie.readFileFromJson("satellite-detail.json")!!
        var jsonFromList = Gson().fromJson(jsonList) as MutableList<Satellite>
        var jsonFromListDetail = Gson().fromJson(jsonListDetail) as MutableList<Satellite>
        var nSatellite: Satellite? = null
        jsonFromListDetail.forEachIndexed {index, it->
            if(it.id == selectedId)
                nSatellite = Satellite(it.id,jsonFromList[index].active,jsonFromList[index].name,it.cost_per_launch,it.first_flight,it.height,it.mass)
        }
        return nSatellite
    }

    // Uydu Son Pozisyonları
    override suspend fun getSatellitesPosition(
        context: Context,
        satelliteid: Int,
        positionId: Int
    ): Position {
        var selectedPosition = Position(0.0,0.0)
        var readFie = ReadFile(context)
        var stListFile = readFie.readFileFromJson("positions.json")!!
        var stListJson = Gson().fromJson(stListFile) as Example
        stListJson.list.forEachIndexed {index, it->
            if(it.id == satelliteid){
                var postions :List<Positions> = it.positions as List<Positions>
                postions.forEachIndexed { index,it->
                   if(index == positionId){
                       selectedPosition = Position(it.posX,it.posY)
                   }
                }
            }
        }
        return selectedPosition
    }
}