mvn clean compile 
mvn spring-boot:run

kafka-topics --list --bootstrap-server localhost:9092


kafka-console-consumer --topic produtor-create-topic --from-beginning --group unico2 --bootstrap-server localhost:9092
kafka-console-consumer --topic produtor-process-topic --from-beginning --group unico2 --bootstrap-server localhost:9092


kafka-topics --delete --topic  produtor-create-topic --bootstrap-server localhost:9092
kafka-topics --delete --topic  produtor-process-topic --bootstrap-server localhost:9092


POST
curl --location --request POST 'http://192.168.100.121:8000/v1/orders' \
--header 'Content-Type: application/json' \
--header 'correlation-id: 1' \
--data-raw '{
"name" : "Marinho321",
"cpfCnpj" : "12345678901",
"idProduct": 1,
"price": 15.55
}'

