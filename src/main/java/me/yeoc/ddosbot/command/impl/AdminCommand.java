package me.yeoc.ddosbot.command.impl;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.Command;
import me.yeoc.ddosbot.object.CDK;
import me.yeoc.ddosbot.util.TimeUtil;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.UUID;

public class AdminCommand extends Command {
    public AdminCommand() {
        super("admin","管理员指令","/admin");
    }

    @Override
    public String getIdentity() {
        return null;
    }

    @Override
    protected boolean limited() {
        return false;
    }

    @Override
    public boolean toReg() {
        return false;
    }

    String[] help = {""};

    @Override
    protected void perform(Message message) {
//        System.out.println("aaa");
        if (!Main.getAdminUID().contains(message.getFrom().getId())){
            sendMessage(message.getChatId(),"你没有管理员权限!");
            return;
        }
        sendMessage(message.getChatId(),"你拥有!");

        String[] split = message.getText().split(" ");
        if (split.length == 0){
            sendMessage(message.getChatId(),help);
        }

        switch (split[1]) {
            case "CDK":
                CDK cdk;
                switch (split[2].toLowerCase()) {
                    case "adda":
                        cdk = new CDK(UUID.randomUUID().toString().replace("-", "").substring(0, 16), "时间", TimeUtil.parseTime(split[3]), false);
                        Main.getDatabase().setCDK(cdk);
                        Main.getDatabase().saveData();
                        sendMessage(message.getFrom().getId(),"添加时间卡成功!","时长: "+TimeUtil.getTime((int) (cdk.getContent()/1000)),"卡密: "+cdk.getText());
                        break;
                    case "addb":
                        cdk = new CDK(UUID.randomUUID().toString().replace("-", "").substring(0, 16), "点数", Integer.parseInt(split[3]), false);
                        Main.getDatabase().setCDK(cdk);
                        Main.getDatabase().saveData();
                        sendMessage(message.getFrom().getId(),"添加点数卡成功!","点数: "+cdk.getContent(),"卡密: "+cdk.getText());
                        break;
                }
                break;
            case "toggle":
                Main.getConfig().set("attackAble",!Main.isAttackAble());
                reloadConfig();
                sendMessage(message.getChatId(),"你已将攻击状态调至: "+Main.isAttackAble());
                break;
            case "reason":
                String newReason = getLeft(split, " ", 2);
                Main.getConfig().set("reason", newReason);
                reloadConfig();
                sendMessage(message.getChatId(),"你已将暂停攻击原因调至: "+newReason);
                break;
            default:
                sendMessage(message.getChatId(),help);

        }
    }
    private static String getLeft(String[] args,String cr,int start){
        StringBuilder s = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            s.append(args[i]);
            if (i != args.length-1) s.append(cr);
        }
        return s.toString();
    }

    public void reloadConfig(){
        Main.saveConfig();
        Main.loadConfig();

    }
}
