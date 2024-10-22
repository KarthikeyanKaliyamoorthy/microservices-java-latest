
#Starts with base image containing java virtual machine
FROM openjdk:21-jdk-slim

#Maintainer details
LABEL "org.opencontainers.image.authors"="karthieee.com"

#Copy the jar file to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Run the jar file
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]