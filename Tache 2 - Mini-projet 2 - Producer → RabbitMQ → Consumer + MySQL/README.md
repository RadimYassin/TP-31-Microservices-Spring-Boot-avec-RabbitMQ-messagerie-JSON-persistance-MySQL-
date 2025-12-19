# Mini-projet 2: Messagerie User avec Persistance MySQL

Ce mini-projet étend le concept du mini-projet 1 en ajoutant la persistance des messages (objets User) dans une base de données MySQL.

## Architecture

```
Producer (8091) → RabbitMQ (Exchange: user_mysql_exchange) → Consumer (8092) → MySQL (tp31_db)
                     ↓
                  Queue: user_mysql_queue
```

## Composants

### Producer
- **Port**: 8091
- **Endpoint**: `POST /api/users/publish`
- **Fonction**: Publie des objets User (JSON) vers RabbitMQ

### Consumer
- **Port**: 8092
- **Fonction**: Consomme les Users de la queue et les persiste dans MySQL
- **Base de données**: `tp31_db`
- **Table**: `users`

## Configuration

### RabbitMQ
- **Exchange**: `user_mysql_exchange` (type: Direct)
- **Queue**: `user_mysql_queue` (durable)
- **Routing Key**: `user_mysql_routing_key`
- **Message Converter**: Jackson2JsonMessageConverter avec JavaTimeModule

### MySQL
- **Base de données**: `tp31_db` (créée automatiquement)
- **Table**: `users` (id, name, email, age, created_at)
- **Port**: 3306
- **Credentials**: root/root (modifiable dans application.properties)

## Démarrage

### 1. Démarrer RabbitMQ avec Docker

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

### 2. Démarrer MySQL avec Docker

```bash
docker run -d --name mysql-tp31 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0
```

**Ou avec phpMyAdmin:**

```bash
docker run -d --name phpmyadmin --link mysql-tp31:db -p 8080:80 phpmyadmin/phpmyadmin
```

Accéder à phpMyAdmin: http://localhost:8080
- Serveur: `mysql-tp31`
- Utilisateur: `root`
- Mot de passe: `root`

### 3. Démarrer le Consumer

```bash
cd consumer
mvn spring-boot:run
```

Le Consumer démarre sur le port 8092 et crée automatiquement la table `users` dans MySQL.

### 4. Démarrer le Producer

```bash
cd producer
mvn spring-boot:run
```

Le Producer démarre sur le port 8091.

## Test avec Postman

### Publier un User

**Endpoint**: `POST http://localhost:8091/api/users/publish`

**Headers**:
```
Content-Type: application/json
```

**Body** (JSON):
```json
{
  "name": "Alice Dupont",
  "email": "alice.dupont@example.com",
  "age": 25
}
```

**Réponse attendue**:
```
User 'Alice Dupont' publié avec succès vers RabbitMQ. Il sera persisté dans MySQL par le Consumer.
```

### Vérifier la persistance

#### Dans les logs du Consumer:
```
========================================
📨 USER REÇU DE RABBITMQ !
========================================
👤 Nom: Alice Dupont
📧 Email: alice.dupont@example.com
🎂 Âge: 25
========================================
💾 PERSISTANCE RÉUSSIE !
========================================
🆔 ID en base: 1
👤 Nom: Alice Dupont
📧 Email: alice.dupont@example.com
========================================
```

#### Dans phpMyAdmin:
1. Accédez à http://localhost:8080
2. Sélectionnez la base `tp31_db`
3. Ouvrez la table `users`
4. Vous devriez voir l'utilisateur Alice Dupont

#### Avec MySQL CLI:
```bash
docker exec -it mysql-tp31 mysql -uroot -proot

USE tp31_db;
SELECT * FROM users;
```

## Observation dans RabbitMQ UI

1. Accédez à http://localhost:15672
2. Allez dans l'onglet **Exchanges**
   - Vous devriez voir `user_mysql_exchange`
3. Allez dans l'onglet **Queues**
   - Vous devriez voir `user_mysql_queue`
   - Observez les compteurs (messages publiés et consommés)

## Gestion des doublons

Le Consumer gère intelligemment les emails en double:
- Si l'email existe déjà, l'utilisateur est **mis à jour** (nom et âge)
- Sinon, un **nouvel utilisateur** est créé

## Points clés d'apprentissage

✅ Déclaration dynamique d'exchange, queue et binding  
✅ Publication d'objets User JSON via RabbitTemplate  
✅ Consommation automatique avec @RabbitListener  
✅ Sérialisation/désérialisation JSON avec Jackson (LocalDateTime)  
✅ Persistance dans MySQL via Spring Data JPA  
✅ Gestion transactionnelle avec @Transactional  
✅ Gestion des doublons avec vérification d'email
