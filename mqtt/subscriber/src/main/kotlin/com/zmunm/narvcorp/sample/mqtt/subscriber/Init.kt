package com.zmunm.narvcorp.sample.mqtt.subscriber

import com.zmunm.narvcorp.sample.mqtt.model.MqttModel
import com.zmunm.narvcorp.sample.core.option.BaseOption
import com.zmunm.narvcorp.sample.core.option.BaseOption.Property
import com.zmunm.narvcorp.sample.mqtt.subscriber.controller.MqttSubscriber
import org.apache.commons.daemon.Daemon
import org.apache.commons.daemon.DaemonContext

val baseOption = BaseOption()
fun main(args: Array<String>) {
	if(baseOption.init(args)) Init.start()
}

fun stop(args: Array<String>) {
	Init.stop()
}

object Init:Daemon{
    private var serviceThread:Thread? = null
	private val controller : MqttModel.Subscribe by lazy{
        MqttSubscriber(MqttModel(baseOption.map[Property.Domain.name]
				?: Property.Domain.value).apply {
			quietMode = baseOption.map[Property.LogLevel.name] ?: Property.LogLevel.value.toInt() == 2
			ssl = (baseOption.map[Property.SSL.name]
					?: Property.SSL.value).toBoolean()
			port = (baseOption.map[Property.Port.name]
					?: Property.Port.value).toInt()
			userName = baseOption.map[Property.UserName.name] ?: Property.UserName.value
			password = baseOption.map[Property.Password.name] ?: Property.Password.value
		}).mqttAdapter {
			println(it)
		}}
	private val service = Runnable{
		println("service init")
		try{
			while(!Thread.currentThread().isInterrupted){
				Thread.sleep(1000L)
				//Thread.sleep(baseOption.map[Property.LoopInterval.name].toRealTimeQuery().toLong())
			}
		}catch(e:InterruptedException){
			destroy()
			println("interrupt")
		}
	}

	override fun init(arg0:DaemonContext){
		println("linux")
	}

	override fun start(){
		controller.connect()
		controller.subscribe("topic")
		serviceThread = Thread(service).apply{start()}
	}

	override fun stop(){
        serviceThread?.interrupt()
	}

	override fun destroy(){
		serviceThread = null
		controller.disconnect()
	}
}
