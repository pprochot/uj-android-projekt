package com.github.pprochot.uj.android.mappers

interface RealmModelMapper<DataModel, out RealmModel> {

    fun mapList(list: List<DataModel>): List<RealmModel>
    fun map(obj: DataModel): RealmModel
}