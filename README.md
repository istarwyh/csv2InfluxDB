# csv2InfluxDB
a service for transfering csv to InfluxDB
# Configuration
In `application.properties`:
```properties
#HOST为系统环境变量需要配置,另注意如果使用本地InfluxDB2.0以下,默认端口8086,2.0以上9999
spring.influx.url=${HOST:http://47.101.146.76:8086}
spring.influx.database=metis
spring.influx.retentionPolicy=autogen
spring.influx.user=root
spring.influx.password=root
spring.influx.token=u2Xonr9XRJliwwsURBCRju4EZeWmToO5hBAwzBryCekmIhQueNKRFwAsaYKdbKLMxwi0QaViG8AHHmBSBTsAyA==
spring.influx.org=87e2941a63ad495d
spring.influx.bucket=05d5f0d32448f000
spring.influx.cloudUrl=https://us-central1-1.gcp.cloud2.influxdata.com
filePath=./repository/test.csv
```