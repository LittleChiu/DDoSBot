package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import me.yeoc.ddosbot.object.CDK;
import me.yeoc.ddosbot.object.User;
import me.yeoc.ddosbot.util.TimeUtil;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CDKCommand extends Command {
    public CDKCommand() {
        super("CDK","激活CDK","CDK");
    }

    @Override
    public String getIdentity() {
        return "CDK";
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
        System.out.println("111");
        Long id = message.getFrom().getId();
        if (!Main.getDatabase().existUser(id)) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 你还没有注册!");
            return;
        }
        String text = message.getText();
        String[] s = text.split(" ");
        if (s.length != 2){
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 参数错误!");
            return;
        }
        boolean b = Main.getDatabase().existCDK(s[1]);
        if (!b){
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 没有这个CDK!");
            return;
        }
        CDK cdk = Main.getDatabase().getCDK(s[1]);
        if (!cdk.canUse()) {
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 这个CDK已使用!");
            return;
        }

        User user = Main.getDatabase().getUser(id);
        System.out.println(cdk.getType());
        if (cdk.getType().equals("时间")) {

            user.addExpireTime((int) cdk.getContent());
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 使用成功!","增加时间: "+ TimeUtil.getTime((int) cdk.getContent()/1000));
        }else if(cdk.getType().equals("点数")){
            System.out.println("c");
            user.addPoint((int) cdk.getContent());
            System.out.println("a");
            sendMessage(message.getChatId(),"@"+message.getFrom().getUserName()+" 使用成功!","增加点券: "+cdk.getContent());
            System.out.println("b");
        }
        Main.getDatabase().saveUser(user);
        cdk.setUsed(true);
        Main.getDatabase().setCDK(cdk);


    }
}
