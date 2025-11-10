package com.verma.loginapi.repository;

import com.verma.loginapi.model.Chat;
import com.verma.loginapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByUser1AndUser2(User user1, User user2);
    Optional<Chat> findByUser2AndUser1(User user2, User user1);
}
