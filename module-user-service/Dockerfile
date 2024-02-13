FROM azul/zulu-openjdk:17

ENV APPDIR=/opt/app/user-service/classes

WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom -cp .:${APPDIR}/* com.joosangah.userservice.UserServiceApplication

EXPOSE 8080 5005 8761