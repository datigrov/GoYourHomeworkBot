package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

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

//            String responseText;
//            switch (textMessage) {
//                case "/start" -> responseText = String.format("Hi!");
//                case "/stop" -> responseText = String.format("Goodbye!");
//                default -> responseText = "I dont understand";
//            }

            if (update.)


        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
