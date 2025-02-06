package pro.sky.telegrambot;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.notification.NotificationTaskClass;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.service.MessageServiceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SendingNotification {

    private final NotificationRepository runNotificationRepository;
    private final MessageServiceImpl messageService;
    private final NotificationTaskClass notificationTaskClass;

    public SendingNotification(NotificationRepository runNotificationRepository,
                               MessageServiceImpl messageService,
                               NotificationTaskClass notificationTaskClass) {
        this.runNotificationRepository = runNotificationRepository;
        this.messageService = messageService;
        this.notificationTaskClass = notificationTaskClass;
    }

    @Scheduled(fixedDelay = 60000)
    public void runNotification() {
        LocalDateTime runLocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTaskClass> notificationRunListMessages =
                runNotificationRepository.findByNotificationTask(runLocalDateTime);

        messageService.info(
                notificationTaskClass.getNotificationMessage(),
                notificationTaskClass.getChatId()
        );

    }
}
