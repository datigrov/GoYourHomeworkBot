package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.notification.NotificationTaskClass;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTaskClass, Long> {
    List<NotificationTaskClass> findByLocalDateTimeNotification(LocalDateTime localDateTimeNotification);
}
