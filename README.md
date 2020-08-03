# businessduration
API that returns total number of business seconds between two given times.

###info
Written in Java, using Springboot

###environment setup
1) Ensure maven (https://maven.apache.org/) is installed on deployment server/local machine
2) From a terminal/command line, navigate to project root (you should see pom.xml file)
3) Execute 'mvn install'

###deployment
From the project root, execute `./mvnw spring-boot:run`.

###how to use api
* <b>url:</b> http://{host_name}:8081/duration (eg. http://localhost:8081/duration)
* <b>expected request format:</b> http://{host_name}:8081/duration?start_time={start_time}&end_time={end_time}
    * <b> Times are in iso-8601 format 'yyyy-MM-ddThh:mm:ss' e.g.: 2020-04-27T07:58:30