package com.ailurusrp.panda_todo.features.home.data.model

interface Task {
    var UUID: String
    var name: String
    var creationDate: Long?
    var completed: Boolean
}