FROM quay.io/wildfly/wildfly:26.1.3.Final-jdk17

RUN curl -o /opt/jboss/wildfly/standalone/deployments/postgresql-42.7.10.jar \
    https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.10/postgresql-42.7.10.jar

COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
COPY target/alquiler-camiones-api-1.0.0.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]