FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y software-properties-common;

RUN add-apt-repository ppa:openjdk-r/ppa -y && \
    apt-get update && apt-get install -y openjdk-11-jdk && \
    apt-get install -y ant && \
    apt-get clean; 
RUN apt-get install maven -y;

WORKDIR /project/compiler/
