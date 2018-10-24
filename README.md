YamlMqttClient
--------------
# 1. :core
    
## 1.1 Dependency

# 2. :mqtt:publisher

    MQTT Publisher
    
## 2.1 Dependency

* [shadowJar](https://github.com/johnrengelman/shadow) - to make a [fatjar](https://stackoverflow.com/questions/19150811/what-is-a-fat-jar)

## 2.2 YML Config

> option.yml

Name           |Explanation    
---          |---          
LogLevel       |0 = Debug , 1 = Default, 2 = Mute
Domain         |just plain domain
Port           |broker's MQTT port
SSL            |true or false
UserName       |UserName
Password       |Password
LoopInterval   |LoopInterval
LoopCount      |LoopCount

> here is example

```YAML
option:
   LogLevel: 0
   Domain: your.domain
   Port: 1883
   SSL: false
   UserName: root
   Password: 1234
   LoopInterval: 3000
   LoopCount: 3
```

> data.yml 
>   > define individual topic and list up your data(hex byte array)

```YAML
/topic:
  - a1b2c3d4e5f6
  - ab,cd,11,22,33,44
```
> you can make some data with `,`. It will be removed when publish 
# 4. :matt:subscriber

    MQTT Subscriber
    
## 3.1 Dependency (Learning curve)

* [shadowJar](https://github.com/johnrengelman/shadow) - to make a [fatjar](https://stackoverflow.com/questions/19150811/what-is-a-fat-jar)
* [Apache Daemon](https://commons.apache.org/proper/commons-daemon/) - to run on background
    - [Procrun](https://commons.apache.org/proper/commons-daemon/procrun.html) - it makes window service from .jar
* [Window batch script](https://www.lesstif.com/pages/viewpage.action?pageId=17105830) - to change Procrun configuration file

## 3.2 YML Config

> option.yml

Name           |Explanation    
---          |---
LogLevel       |0 = Debug , 1 = Default, 2 = Mute
Domain         |just plain domain
Port           |broker's MQTT port
SSL            |true or false
UserName       |UserName
Password       |Password

> here is example

```YAML
option:
  LogLevel: 0
  Domain: your.domain
  Port: 1883
  SSL: false
  UserName: root
  Password: 1234
```