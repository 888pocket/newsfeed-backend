FROM azul/zulu-openjdk:11

ENV APPDIR=/opt/app/newsfeed-service/classes

WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007"

CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom  -cp .:${APPDIR}/* com.joosangah.newsfeedservice.NewsfeedServiceApplication

EXPOSE 8082 5007