package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AboutCommand extends Command {
    public AboutCommand() {
        super("about","关于本机器人","/about","关于","/关于");
    }

    @Override
    public String getIdentity() {
        return "About";
    }

    @Override
    protected boolean limited() {
        return false;
    }

    @Override
    protected void perform(Message message) {
        sendMessage(message.getChatId(), Main.getAboutMessage());
    }
}
