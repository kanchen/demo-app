# demo-app

##create a java web service with jersey project
mvn archetype:generate \
  -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
  -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
  -DgroupId=com.dekses -DartifactId=demo-app \
  -Dpackage=com.dekses.jersey.docker.demo -DarchetypeVersion=2.22.2

##build the project
mvn install

##run 
mvn exec:java

##test
curl http://localhost:8080/demo-app/helloworld

##edit src/main/java/com/dekses/jersey/docker/demo/Main.java
change localhost to 0.0.0.0

##build Docker image
docker build --tag=demo-app .

##run Docker
docker run -d -p 18080:8080 -t -i demo-app

##test Docker run
curl http://localhost:18080/demo-app/api/info

##unit testing
mvn test

## coverage
mvn clean test
mvn jacoco:report

## sonar
mvn jacoco:report sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -DskipTests=true
