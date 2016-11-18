
echo "########################### Auth-service ######################"
cd auth-service
./gradlew build buildDocker
cd ..

echo "########################### Configserver ######################"
cd configserver
./gradlew build buildDocker
cd ..

echo "########################### Eurekaserver ######################"
cd eureka-server
./gradlew build buildDocker
cd ..

echo "########################### Exporterservice ######################"
cd exporter-service
./gradlew build buildDocker
cd ..

echo "########################### midserver ######################"
cd midserver
./gradlew build buildDocker
cd ..

echo "########################### Zuulproxy ######################"
cd zuul-proxy
./gradlew build buildDocker
cd ..

echo "All completed!"