FROM azul/zulu-openjdk:11

ENV APPDIR=/opt/app/activity-service/classes

WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006"

CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom  -cp .:${APPDIR}/* com.joosangah.activityservice.ActivityServiceApplication

EXPOSE 8081 5006 8761