package pro.sky.telegrambot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(SendingNotification.class);
    private final NotificationRepository runNotificationRepository;
    private final MessageServiceImpl messageService;

    public SendingNotification(NotificationRepository runNotificationRepository,
                               MessageServiceImpl messageService) {
        this.runNotificationRepository = runNotificationRepository;
        this.messageService = messageService;
    }

    @Scheduled(fixedDelay = 60000)
    public void runNotification() {
        LocalDateTime runLocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTaskClass> notificationRunListMessages =
                runNotificationRepository.findAllByNotificationLocalDateTime(runLocalDateTime);

        logger.info("We have {} notification tasks", notificationRunListMessages.size());

        for (NotificationTaskClass notificationTaskClassMessage : notificationRunListMessages) {
            messageService.info(notificationTaskClassMessage.getChatId(),
                    notificationTaskClassMessage.getNotificationMessage());
        }
    }
}
