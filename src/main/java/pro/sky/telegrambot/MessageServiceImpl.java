package pro.sky.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;

import java.util.regex.Pattern;


@Service
public class MessageServiceImpl implements MessageService {

    private TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public MessageServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    @Override
    public void info(String messageText, long chatId) {
        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = telegramBot.execute(message);

        if (response.isOk()) {
            logger.info("message {} and id {}", messageText, chatId);
        } else {
            logger.warn("Something is wrong");
        }
    }

}

