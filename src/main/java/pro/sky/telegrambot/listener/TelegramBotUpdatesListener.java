package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.MessageServiceImpl;
import pro.sky.telegrambot.notification.NotificationTaskClass;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final Pattern patternMessage = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    private final NotificationRepository notificationRepository;
    private final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final String RESPONSE_TEXT = "HI, im your telegram bot";
    private final String WRONG_ON_TEXT = "Wrong message";
    private final String endMessage = "Notification is added";

    @Autowired
    private MessageServiceImpl service;

    public TelegramBotUpdatesListener(NotificationRepository notificationRepository,
                                      MessageServiceImpl service, TelegramBot telegramBot) {
        this.notificationRepository = notificationRepository;
        this.service = service;
        this.telegramBot = telegramBot;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String textMessage = update.message().text();
            long chatId = update.message().chat().id();

            if (textMessage.equals("/start")) {
                service.info(RESPONSE_TEXT,chatId);
            } else {
                Matcher matcher = patternMessage.matcher(textMessage);
                if (matcher.matches()) {
                    NotificationTaskClass notificationTaskClass = new NotificationTaskClass();
                    notificationTaskClass.setId(chatId);
                    notificationTaskClass.setNotificationMessage(matcher.group(3));
                    notificationTaskClass.setLocalDateTimeNotification(LocalDateTime.parse(matcher.group(1),DATE_TIME));
                    notificationTaskClass = notificationRepository.save(notificationTaskClass);
                    service.info(endMessage, chatId);
                }else {
                    service.info(WRONG_ON_TEXT,chatId);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
