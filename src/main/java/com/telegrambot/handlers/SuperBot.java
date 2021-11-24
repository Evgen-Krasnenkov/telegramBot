package com.telegrambot.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class SuperBot extends TelegramLongPollingBot {

    private static final String WHAT_TIME_IS_IT = "What time is it?";
    public static final String DO_YOU_WANNA_PLAY = "Do you wanna play?";
    public static final String DO_YOU_WANNA_SCHEDULE_A_GAME = "Do you wanna schedule a game?";
    public static final String EXIT = "Exit?";
    public static final String TETRIS = "Tetris";
    public static final String POKER = "Poker";
    public static final String FOOL = "Fool";
    public static final String SEEK_HIDE = "Seek & Hide";

    @Override
    public String getBotUsername() {
        return "clmTeamBot";
    }

    @Override
    public String getBotToken() {
        return "2137351575:AAFxVw3AjFuDGGLAh0iOqy1k_u5fLQpj29c";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        execute(getResponseMessage(message));
    }

    private ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup customMarkup = new ReplyKeyboardMarkup();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(DO_YOU_WANNA_PLAY);
        firstRow.add(DO_YOU_WANNA_SCHEDULE_A_GAME);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(WHAT_TIME_IS_IT);
        secondRow.add(EXIT);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(firstRow);
        rows.add(secondRow);
        customMarkup.setKeyboard(rows);
        return  customMarkup;
    }

    private SendMessage getCurrentTime (Message message) {
        SendMessage response = new SendMessage();
        response.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        response.setChatId(String.valueOf(message.getChatId()));
        response.setReplyMarkup(getMainMenu());
        return response;
    }

    private SendMessage getResponseMessage(Message message) {
        switch (message.getText()) {
            case WHAT_TIME_IS_IT:
                return  getCurrentTime(message);
            case DO_YOU_WANNA_PLAY:
                return getOrderGame(message);
            default:
                return getGreetingMessage(message);
        }
    }

    private SendMessage getGreetingMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        String firstName = message.getFrom().getFirstName();
        response.setText("Hello, " + firstName +  ". You've chosen - " + message.getText());
        response.setChatId(String.valueOf(chatId));
        response.setReplyMarkup(getMainMenu());
        return  response;
    }

    private SendMessage getOrderGame(Message message) {
        SendMessage response = new SendMessage();
        response.setText("What do you like?");
        response.setReplyMarkup(getGameMenu());
        response.setChatId(String.valueOf(message.getChatId()));
        return  response;
    }

    private ReplyKeyboardMarkup getGameMenu() {
        ReplyKeyboardMarkup gameMarkup = new ReplyKeyboardMarkup();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(TETRIS);
        firstRow.add(POKER);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(FOOL);
        secondRow.add(SEEK_HIDE);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(firstRow);
        rows.add(secondRow);
        gameMarkup.setKeyboard(rows);
        return  gameMarkup;
    }
}
