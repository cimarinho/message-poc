Kafka x Redpanda

**Criando duas Rotas .**

**/v1/flow (post)**-> recebe e valida uma mensagem para o kafka|redpanda. Detalhe o header transactionId é obrigatorio.

**/v1/flow/1234564** (get)-> Busca uma mensagem no kafka com o transactionId.

**http://localhost:9644/metrics**  Metricas redpanda

<br/>
Para aplicação é transparente a configuração do kafka ou redpanda. No diretorio docker tem as duas configurações.

**redpanda**: 
    Executar dockker-compose -up
    Esta configurado o prometheus e grafana.

**kafka**
    Executar dockker-compose -up

**Para testar os endpoints tem uma collection do postman poc-message.postman_collection.json**

<br/>

**Comandos:**
mvn clean compile 
mvn spring-boot:run

kafka-topics --list --bootstrap-server localhost:9092


kafka-console-consumer --topic produtor-create-topic --from-beginning --group unico2 --bootstrap-server localhost:9092
kafka-console-consumer --topic produtor-process-topic --from-beginning --group unico2 --bootstrap-server localhost:9092


kafka-topics --delete --topic  produtor-create-topic --bootstrap-server localhost:9092
kafka-topics --delete --topic  produtor-process-topic --bootstrap-server localhost:9092


curl

<br/>
curl --location --request POST 'http://localhost:8080/v1/flow' \
--header 'Content-Type: application/json' \
--header 'transactionId: 1234564' \
--data-raw '{
    "name" : "Marinho",
    "cpfCnpj" : "12345678901",
    "idProduct": 1,
    "price": 15.55
}'

<br/>
http://localhost:8080/v1/flow/1234564

<br/>
http://localhost:9644/metrics