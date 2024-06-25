### Ejecucion de docker

#### Objectivo

El objectivo es compilar el código y generar una imagen docker el cual podamos arrancar.

#### Compilando codigo:
```shell
$ cd {path-project}
$ mvn clear install -DskipTests -Dmaven.test.skip=true
```

#### Generando imagen docker [DockerFile](docker/Dockerfile)
Utilizaremos el Dockerfile para generar la imagen docker.

```shell
$ sudo docker image build -t quartz-cimacg:1.0.0 -f Dockerfile .
```

```markdown
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
```

Ahora, ejecutaremos el docker-compose para levantar la imagen docker.

```shell
$ docker comopose -f docker/docker-compose.yml up -d
```
Call to: http://localhost:28080/swagger-ui.html

Listao de images docker:

```shell
$ docker images
```

#### File docker-exec.sh

```shell
$ ./docker-exec.sh
```