package com.zmunm.narvcorp.sample.core.dto

data class Admin (
		var id:String,
        var password:String? = null,
        var name:String? = null,
        var email:String? = null
){
    constructor(
            id:String,
            password:String
    ):this(id)

    data class EnvironmentData(
            val dust:Float?,
            val co2:Float?,
            val temp:Float?,
            val humi:Float?
    )
}
