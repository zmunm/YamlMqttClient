package com.zmunm.narvcorp.sample.core.mysql.option

import com.zmunm.narvcorp.sample.core.define.BaseDefine

enum class DatabaseOption (
        private val driverClassName: String,
        val url: String,
        private val userName: String,
        private val password: String,
        private val initialSize: Int,
        private val maxActive: Int,
        private val maxIdle: Int,
        private val minIdle: Int,
        private val maxWait: Int
){
    Local(
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://${BaseDefine.LOCAL_URL}:3306/sample?allowMultiQueries=true",
            "root",
            "1234",
            10,
            10,
            10,
            10,
            3000
    );
    fun configureDataSource(dataSource: org.apache.tomcat.jdbc.pool.DataSource) =
            dataSource.also {
                it.driverClassName = driverClassName
                it.url = url
                it.username = userName
                it.password = password
                it.maxActive = maxActive
                it.initialSize = initialSize
                it.maxIdle = maxIdle
                it.minIdle = minIdle
                it.maxWait = maxWait
                it.validationQuery = "select 1"
                it.isTestOnBorrow = false
                it.isTestOnReturn = false
            }
}
