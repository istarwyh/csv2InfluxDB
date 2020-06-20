# csv2InfluxDB
a service for transfering csv to InfluxDB
# Configuration
In `application.properties`:
```properties
#HOST为系统环境变量需要配置,另注意如果使用本地InfluxDB,则默认端口9999
spring.influx.url=${HOST:http://47.101.146.76:8086}
spring.influx.database=metis
spring.influx.retentionPolicy=autogen
spring.influx.user=root
spring.influx.password=root
```