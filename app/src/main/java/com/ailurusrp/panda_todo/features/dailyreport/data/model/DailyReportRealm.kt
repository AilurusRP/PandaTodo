package com.ailurusrp.panda_todo.features.dailyreport.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class DailyReportRealm() : RealmObject {
    var creationDate: String = ""
    var completedTasks: RealmList<String> = realmListOf()
}