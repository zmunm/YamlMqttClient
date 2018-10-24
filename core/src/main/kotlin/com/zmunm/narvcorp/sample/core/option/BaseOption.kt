package com.zmunm.narvcorp.sample.core.option

import com.zmunm.narvcorp.sample.core.define.BaseDefine
import org.yaml.snakeyaml.Yaml
import java.io.File
import javax.naming.NoInitialContextException

class BaseOption{
    enum class Property(
            val value:String,
            val explain:String
    ){
        LogLevel(BaseDefine.LogMode.Debug.ordinal.toString(),"Debug=0 or Default=1 or Mute=2"),
        Domain(BaseDefine.LOCAL_MQTT_URL,"String domain like 192.168.0.1 or example.com"),
        Port(1883.toString(),"Integer"),
        SSL(false.toString(),"Boolean"),
        UserName("root","userName to access"),
        Password("1234","password to access"),
        LoopInterval(3000L.toString(),"loop time (millisecond)"),
        LoopCount(1.toString(),"loop count (-1 = endless)")
    }
    val map = Property.values().map { it.name to it.value }
            .toMap()
            .toMutableMap()

    private val help = Property.values().joinToString { "--${it.name} : ${it.explain}\n" }
            .plus("\n-yml : It ignores all command line options and uses the 'option.yml' file in same directory.\n")

    fun init(args:Array<String>) = try {
        args.filter {
            !it.startsWith("-yml").apply { load("option.yml",it) }
        }.map {
            it.split("=".toRegex()).takeIf { it[0].startsWith("--") }
                    ?: throw NoInitialContextException("입력 실패 $it\n$help")
        }.map {
            Property.valueOf(it[0].replace("--", ""))
        }.forEach {
            map[it.name] = it.value
        }
        true
    } catch (e: NoInitialContextException) {
        println(e)
        false
    } catch (e: IllegalArgumentException){
        println("$e 없는 옵션 \n$help")
        false
    }

    private fun load(path:String,option:String) :Boolean {
        try {
            Yaml().loadAll(File("""${System.getProperty("user.dir")}\$path""")
                    .readLines()
                    .joinToString("\n")
            )
                    .first()
                    .let {
                        it as? LinkedHashMap<*, *>?:throw TypeCastException("YAML 파싱 오류")
                    }[option.split("=").takeIf { it.size==2 }?.get(1)?:"option"]
                    .let { it?:TypeCastException("카테고리 없음 - 기본 = option\n") }
                    .let { it as? LinkedHashMap<*, *>?:throw TypeCastException("카테고리 오류\n$it") }
                    .forEach {
                        map[it.key.toString()]?.run {
                            println("initialize option --${it.key}")
                            map[it.key.toString()] = it.value.toString()
                        }?:throw TypeCastException("없는 옵션 ${it.key}\n------you must use these properties------\n$help")
                    }
            return true
        }catch (e:TypeCastException){
            println(e)
            return false
        }
    }
}
