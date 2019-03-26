# Pull base image
FROM openjdk:8-jre-alpine

MAINTAINER amine.elkouhen@gmail.com

RUN mkdir -p /opt/app

# Copy sources
COPY build/libs/Mino.jar /opt/app/

# Define working directory
WORKDIR /opt/app/

EXPOSE 8081

VOLUME /var/lib/carhub

ENTRYPOINT ["java", "-jar", "/opt/app/Mino.jar"]
