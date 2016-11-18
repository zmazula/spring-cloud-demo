FROM codenvy/ubuntu_jdk8
VOLUME /tmp
ADD eureka-server-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh wait-for-it.sh
#RUN bash -c 'touch /app.jar'
#ENV JAVA_OPTS=""
#ENTRYPOINT [ "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
#CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar