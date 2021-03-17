# csv2InfluxDB
a service for transfering csv to InfluxDB
## Configuration
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
## Other Mapper:
This can be imported by Postman:
```json5
{
	"info": {
		"_postman_id": "afe618aa-78a8-4fc9-aac4-c14527a60e1a",
		"name": "csv2InfluxDB",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "greeting",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8080/operator/greeting",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8080",
					"path": [
						"operator",
						"greeting"
					]
				}
			},
			"response": []
		},
		{
			"name": "root",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8081/",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						""
					]
				}
			},
			"response": []
		},
		{
			"name": "queryAllUser",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8081/",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						""
					]
				}
			},
			"response": []
		},
		{
			"name": "monitor insert",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"traceId\":\"\",\r\n    \"spanId\":\"3\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://127.0.0.1:8081/monitor/insert",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						"monitor",
						"insert"
					]
				}
			},
			"response": []
		},
		{
			"name": "业务异常",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8081/operator/business",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						"operator",
						"business"
					]
				}
			},
			"response": []
		},
		{
			"name": "一个实验,其实是我们取代了系统自己的错误处理器",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8081/operator/500",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						"operator",
						"500"
					]
				}
			},
			"response": []
		},
		{
			"name": "monitor monitor",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://127.0.0.1:8081/monitor/add",
					"protocol": "http",
					"host": [
						"127",
						"0",
						"0",
						"1"
					],
					"port": "8081",
					"path": [
						"monitor",
						"add"
					]
				}
			},
			"response": []
		}
	]
}
```