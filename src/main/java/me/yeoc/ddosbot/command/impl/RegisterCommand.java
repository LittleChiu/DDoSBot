package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RegisterCommand extends Command {
    public RegisterCommand() {
        super("register","注册账户","/注册","注册","reg","/register","/reg");
    }

    @Override
    public String getIdentity() {
        return "register";
    }

    @Override
    protected boolean limited() {
        return false;
    }

    @Override
    protected void perform(Message message) {
        if (Main.getDatabase().registerUser(message.getFrom().getId())) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 注册成功!");
        }else {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 注册失败! 你可能已经注册过!");

        }
    }

}
