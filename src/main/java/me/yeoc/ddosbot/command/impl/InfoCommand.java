package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import me.yeoc.ddosbot.object.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.text.SimpleDateFormat;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "查询自己的账户", "/info","查询");
    }

    @Override
    public String getIdentity() {
        return "Info";
    }

    @Override
    protected boolean limited() {
        return false;
    }

    SimpleDateFormat dateFormat= new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    @Override
    protected void perform(Message message) {
        Long id = message.getFrom().getId();
        if (!Main.getDatabase().existUser(id)) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 你还没有注册!");
            return;
        }
        User user = Main.getDatabase().getUser(id);
        sendMessage(message.getChatId(),"@"+message.getFrom().getUserName(),
                "账户UID: "+ id ,
                "点数: "+user.getPoint(),
                "天数卡到期时间: "+ dateFormat.format(user.getExpireTime())
        );

    }
}
