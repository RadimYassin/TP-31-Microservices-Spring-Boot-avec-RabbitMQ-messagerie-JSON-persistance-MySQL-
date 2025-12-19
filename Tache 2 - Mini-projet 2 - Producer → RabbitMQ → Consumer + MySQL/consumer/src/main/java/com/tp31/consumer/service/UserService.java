package com.tp31.consumer.service;

import com.tp31.consumer.dto.UserDto;
import com.tp31.consumer.entity.User;
import com.tp31.consumer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service métier pour gérer les Users
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Sauvegarde un User dans MySQL
     * 
     * @param userDto le DTO User reçu de RabbitMQ
     * @return l'entité User persistée
     */
    @Transactional
    public User saveUser(UserDto userDto) {
        log.info("💾 Tentative de sauvegarde du User: {}", userDto.getEmail());

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.warn("⚠️ L'email {} existe déjà dans la base de données", userDto.getEmail());
            // Option 1: Mettre à jour l'utilisateur existant
            User existingUser = userRepository.findByEmail(userDto.getEmail())
                    .orElseThrow();
            existingUser.setName(userDto.getName());
            existingUser.setAge(userDto.getAge());
            User updated = userRepository.save(existingUser);
            log.info("✅ User mis à jour avec succès: ID={}", updated.getId());
            return updated;
        }

        // Créer un nouvel utilisateur
        User user = new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getCreatedAt());

        User savedUser = userRepository.save(user);
        log.info("✅ User sauvegardé avec succès dans MySQL: ID={}", savedUser.getId());

        return savedUser;
    }
}
