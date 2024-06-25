# mvn clean install -P skip-test -f ../fuentes/pom.xml

ls es.cimacg.quartz/target

cp es.cimacg.quartz/target/*.jar ./gsshorexp.jar

docker build -t gsshorexp:0.0.1-R -f Dockerfile .

rm -rf *.jar

docker compose -f docker-compose.yml up -d