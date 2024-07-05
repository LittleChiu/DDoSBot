package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MenuCommand extends Command {
    public MenuCommand() {
        super("menu", "打开攻击菜单","/菜单","菜单","/menu");
    }

    @Override
    public String getIdentity() {
        return "Menu";
    }

    @Override
    protected boolean limited() {
        return false;
    }

    @Override
    protected void perform(Message message) {
        sendMessage(message.getChatId(), Main.getHelpMessage());
    }
}
