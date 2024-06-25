# utilizaremos la imagen de openjdk 17 con alpine ya que es una distribución de linux muy pequeña
FROM  openjdk:17-jdk-alpine
# copiamos el jar generado en la carpeta target a la carpeta /opt/ en la imagen
RUN pwd
COPY es.cimacg.quartz/target/*.jar /opt/

# Las variables de entorno que se pueden configurar son:
ENV SPRING_PROFILE=
ENV PORT 28080
EXPOSE $PORT

# Ejecutamos el jar con las variables de entorno
CMD [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILE} -jar /opt/*.jar" ]