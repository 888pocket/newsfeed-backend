FROM azul/zulu-openjdk:17

ENV APPDIR=/opt/app/api-gateway/classes

WORKDIR ${APPDIR}

ENV JAVA_DEBUG_OPT="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5008"

CMD java ${JAVA_DEBUG_OPT} -Djava.security.egd=file:/dev/./urandom -cp .:${APPDIR}/* com.joosangah.apigateway.ApiGatewayApplication

EXPOSE 8000 5008 8761