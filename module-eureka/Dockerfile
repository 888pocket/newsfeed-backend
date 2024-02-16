FROM azul/zulu-openjdk:17

ENV APPDIR=/opt/app/eureka/classes

WORKDIR ${APPDIR}

CMD java -cp .:${APPDIR}/* com.joosangah.eureka.EurekaApplication

EXPOSE 8761