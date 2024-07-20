# kafka_kotlin_corotinas
Poc consumidor produtor kafka com kotlin usando corrotinas

# Mandar mensagem no topico
Enter no broker e depois produza as mensagens
 - docker-compose broker -it kafka /bin/bash
 - echo "Minha mensagem de exemplo" > message.txt
 - cat message.txt | kafka-console-producer --broker-list localhost:9092 --topic topic1
 - kafka-console-producer --broker-list localhost:9092 --topic comando-abrir-conta


