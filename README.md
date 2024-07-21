# kafka_kotlin_corotinas
Poc consumidor produtor kafka com kotlin usando corotinas

# Mandar mensagem no topico
Entre no broker e depois produza as mensagens
 - docker-compose exec -it broker /bin/bash
 - echo "Minha mensagem de exemplo" > message.txt
 - cat message.txt | kafka-console-producer --broker-list localhost:9092 --topic topic1
 - kafka-console-producer --broker-list localhost:9092 --topic comando-abrir-conta


