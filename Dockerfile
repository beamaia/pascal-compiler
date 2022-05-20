# openjdk 11
FROM openjdk:11

# install wget and build tools
RUN apt-get update && apt-get install -y wget && apt-get install -y build-essential
WORKDIR /project
