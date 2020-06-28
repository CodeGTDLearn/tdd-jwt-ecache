############################
#CONFIG PARA DEBUG ROTATIVO#
############################
#1 ALTERANDO VERSO DO JDK (SE NECESSARIO)
#1.1: IDE - ALTERACOES: (CLIQUE NA PASTA DO PROJETO + TECLA 'F4')
# - Project / Project JDK
# - Project / Project Language Level
# - Modules / Language Level
# - Platform Setting / SDK
# - (IDE): Maven Clean + Package
#1.2: POM - ALTERACOES
# - <java.version>11</java.version>
# - Plugin
#   <artifactId>spring-boot-maven-plugin</artifactId>
#   <configuration>
#       <excludeDevtools>false</excludeDevtools>
#1.3: DOCKER-COMPOSE
# - DEBUG_OPTIONS
#   *JDK11: DEBUG_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -Xmx1G -Xms128m -XX:MaxMetaspaceSize=128m
#   *JDK08: DEBUG_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Xmx1G -Xms128m -XX:MaxMetaspaceSize=128m


#JDK11
#FROM openjdk:11
#JDK8
#a)ALPINE-VERSIONS: They are slim; Thus, makes the process faster
FROM openjdk:8-alpine

WORKDIR /opt/api

#b)TARGET FOLDER: Muste generated beforehand - use mvn clean package
COPY /target/*.jar /opt/api/web-api.jar

SHELL ["/bin/sh", "-c"]

#b)PORTS: b.1)8080 to attach the App by itself; b.2) To attach the debbuger
EXPOSE 8080
EXPOSE 5005

#c)DEBUG_OPTION + PROFILE: Environmental variables comes from Docker-Compose
CMD java ${DEBUG_OPTIONS} -jar web-api.jar --spring.profiles.active=${PROFILE}