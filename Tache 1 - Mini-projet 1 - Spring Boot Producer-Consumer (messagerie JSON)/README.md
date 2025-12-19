# Mini-projet 1: Messagerie JSON avec RabbitMQ

Ce mini-projet démontre la communication asynchrone entre deux microservices Spring Boot via RabbitMQ avec sérialisation JSON.

## Architecture

```
Producer (8081) → RabbitMQ (Exchange: user_exchange) → Consumer (8082)
                     ↓
                  Queue: user_queue
```

## Composants

### Producer
- **Port**: 8081
- **Endpoint**: `POST /api/messages/send`
- **Fonction**: Publie des messages JSON vers RabbitMQ

### Consumer
- **Port**: 8082
- **Fonction**: Consomme les messages de la queue et les affiche dans les logs

## Configuration RabbitMQ

- **Exchange**: `user_exchange` (type: Direct)
- **Queue**: `user_queue` (durable)
- **Routing Key**: `user_routing_key`
- **Message Converter**: Jackson2JsonMessageConverter

## Démarrage

### 1. Démarrer RabbitMQ avec Docker

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Accéder à l'interface RabbitMQ: http://localhost:15672
- Username: `guest`
- Password: `guest`

### 2. Démarrer le Consumer

```bash
cd consumer
mvn spring-boot:run
```

Le Consumer démarre sur le port 8082 et se met en écoute de la queue.

### 3. Démarrer le Producer

```bash
cd producer
mvn spring-boot:run
```

Le Producer démarre sur le port 8081.

## Test avec Postman

### Envoyer un message

**Endpoint**: `POST http://localhost:8081/api/messages/send`

**Headers**:
```
Content-Type: application/json
```

**Body** (JSON):
```json
{
  "content": "Bonjour depuis le Producer!",
  "sender": "Alice"
}
```

**Réponse attendue**:
```
Message publié avec succès: Bonjour depuis le Producer!
```

### Vérifier la réception

Consultez les logs du Consumer, vous devriez voir:
```
========================================
📨 MESSAGE REÇU !
========================================
📝 Contenu: Bonjour depuis le Producer!
👤 Expéditeur: Alice
🕐 Timestamp: 2025-12-19T23:30:00
========================================
```

## Observation dans RabbitMQ UI

1. Accédez à http://localhost:15672
2. Allez dans l'onglet **Exchanges**
   - Vous devriez voir `user_exchange`
3. Allez dans l'onglet **Queues**
   - Vous devriez voir `user_queue`
   - Observez les compteurs de messages

## 📸 Screenshots

### Test avec Postman
![Test Postman - Envoi de message](../../screenshots/postman-test.png)
*Requête POST réussie vers `/api/messages/send` avec réponse "Message publié avec succès"*

### Conteneurs Docker
![Conteneurs Docker](../../screenshots/docker-containers.png)
*RabbitMQ et MySQL en cours d'exécution dans Docker Desktop*

### Interface RabbitMQ
![RabbitMQ Management UI](../../screenshots/rabbitmq-overview.png)
*Vue d'ensemble de RabbitMQ montrant les exchanges, queues et statistiques de messages*

## Points clés d'apprentissage

✅ Déclaration dynamique d'exchange, queue et binding via Spring Boot  
✅ Publication de messages JSON via RabbitTemplate  
✅ Consommation automatique avec @RabbitListener  
✅ Sérialisation/désérialisation JSON avec Jackson2JsonMessageConverter  
✅ Observation des échanges dans l'interface RabbitMQ

