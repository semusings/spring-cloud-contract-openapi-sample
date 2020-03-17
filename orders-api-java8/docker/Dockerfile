FROM ubuntu:19.10 as build
ARG SDKMAN_JAVA_INSTALLATION=8.0.242.hs-adpt

MAINTAINER Bhuwan Prasad Upadhyay <bhuwan.upadhyay49@gmail.com>

RUN apt-get update && apt-get install -y curl \
    unzip \
    zip \
    && apt-get clean

# Install sdkman and java
RUN curl -s https://get.sdkman.io/ | bash
COPY sdkman.config /.sdkman/etc/config
COPY sdkman /usr/local/bin/
RUN /bin/bash -c "chmod +x /usr/local/bin/sdkman-exec.sh && chmod +x /usr/local/bin/sdkman-wrapper.sh && chmod +x /root/.sdkman/bin/sdkman-init.sh"
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh"
RUN sdkman-wrapper.sh install java "${SDKMAN_JAVA_INSTALLATION}"
ENV JAVA_HOME /root/.sdkman/candidates/java/current/
ENV PATH "${PATH}:${JAVA_HOME}/bin"

FROM openjdk:8-jre-alpine as runner
VOLUME /tmp
COPY target/orders-api.jar app.jar
ENTRYPOINT ["java","--enable-preview","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]