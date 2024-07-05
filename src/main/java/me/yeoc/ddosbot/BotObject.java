package me.yeoc.ddosbot;

import lombok.SneakyThrows;
import me.yeoc.ddosbot.command.Command;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class BotObject extends TelegramLongPollingBot {




    public BotObject(DefaultBotOptions botOptions) {
        super(botOptions);

    }


    @Override
    public String getBotUsername() {
        return Main.getBot_name();
    }

    @Override
    public String getBotToken() {
        return Main.getBot_token();
    }

    @Override @SneakyThrows
    public void onRegister() {
        SetMyCommands.SetMyCommandsBuilder builder = SetMyCommands.builder();
        for (Command command : Main.getCommandManager().getCommands()) {
            if (!command.toReg()) continue;
            builder.command(BotCommand.builder().command(command.getName())
                    .description(command.getDescription()).build());
        }
        sendApiMethod(builder
                .build());
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()){
            handleMessage(update);
        }
    }

    private void handleMessage(Update update) {
        System.out.println(update.getMessage());
        Main.getCommandManager().perform(update);

    }


    @SneakyThrows
    public void sendMsg(String text,Long chatId){
        SendMessage ret = new SendMessage();
        ret.setChatId(chatId);
        ret.setText(text);
        execute(ret);
    }
    @SneakyThrows
    public void sendMsgWithCopy(String text,Long chatId){
        SendMessage ret = new SendMessage();
        ret.setChatId(chatId);
        ret.setText(text);

        execute(ret);
    }
}
