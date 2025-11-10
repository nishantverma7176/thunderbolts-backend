package com.verma.loginapi.repository;

import com.verma.loginapi.model.Chat;
import com.verma.loginapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatOrderByTimestampAsc(Chat chat);
}
