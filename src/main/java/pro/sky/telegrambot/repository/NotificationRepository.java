package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.notification.NotificationTaskClass;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTaskClass, Long> {
    @Query("SELECT nt FROM NotificationTaskClass nt WHERE nt.localDateTime = :localDateTime")
    List<NotificationTaskClass> findByNotificationTask(@Param("localDateTime") LocalDateTime localDateTimeNotification);
}
