FROM azul/zulu-openjdk:11

ENV APPDIR=/opt/app/api-gateway/classes

WORKDIR ${APPDIR}

CMD java -cp .:${APPDIR}/* com.joosangah.apigateway.ApiGatewayApplication

EXPOSE 8000 8761