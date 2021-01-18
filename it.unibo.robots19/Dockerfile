#jar cfm HelloWorld.jar manifest.txt HelloWorld.class

##FROM java:8
FROM openjdk:12.0.2

ADD ./build/distributions/it.unibo.robots19-1.0.tar  /

WORKDIR /it.unibo.robots19-1.0/bin

COPY ./*.pl ./
RUN ls


CMD ["bash", "it.unibo.robots19"]


#### docker build -t basicrobot19:1.0 .
#### docker run -p 8010:8010 -ti --rm basicrobot19:1.0 /bin/bash
