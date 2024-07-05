package me.yeoc.ddosbot.command;


import lombok.Getter;
import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.util.StringUtil;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Command {
    private final String name;
    private final String description;
    private final List<String> alias;

    public Command(String name,String description,String... alias){
        this.name = name;
        this.description = description;
        this.alias = Arrays.asList(alias);
    }


    protected CommandManager getCommandManager(){
        return Main.getCommandManager();
    }
    public abstract String getIdentity();

    public boolean toReg(){
        return true;
    }
    protected abstract boolean limited();

    protected abstract void perform(Message message);


    protected void sendMessage(Long chatId,String... str){
        Main.getBotObject().sendMsg(StringUtil.join(str,"\n"),chatId);
    }



}
