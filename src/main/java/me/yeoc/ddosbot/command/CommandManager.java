package me.yeoc.ddosbot.command;


import lombok.Getter;
import lombok.SneakyThrows;

import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.command.impl.*;
import me.yeoc.ddosbot.util.config.Config;
import me.yeoc.ddosbot.util.config.Configuration;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {



    @Getter
    List<Command> commands;

    @Getter
    HashMap<Command,List<String>> limited = new HashMap<>();

    HashMap<String,Command> stringCommandHashMap = new HashMap<>();

    public CommandManager(){
       commands = new ArrayList<>();
    }


    @Getter
    Configuration config;
    public void init() {
        initConfig();


        commands.add(new MenuCommand());
        commands.add(new RegisterCommand());
        commands.add(new InfoCommand());
        commands.add(new AttackCommand());
        commands.add(new AboutCommand());
        commands.add(new AdminCommand());
        commands.add(new CDKCommand());

        commands.stream().filter(Command::limited).forEach(command -> {
        limited.put(command, new ArrayList<>());
        stringCommandHashMap.put(command.getIdentity(),command);
        });

//        Configuration allowed = config.getSection("allowed");
//        for (String key : allowed.getKeys()) {
//            if (stringCommandHashMap.containsKey(key)){
//                allowed.getStringList(key).forEach(s -> limited.get(stringCommandHashMap.get(key)).add(s));
////                limited.get(stringCommandHashMap.get(key).getIdentity()).add()
//            }
//        }
    }

    File file;
    @SneakyThrows
    public void initConfig(){
        file = new File("commands.yml");
        if (!file.exists()) file.createNewFile();
        config = new Config().load(file);
    }
    public void perform(Update event) {
        Message message = event.getMessage();
        String content = message.getText();
        String[] args = content.split(" ");

//        if (args.length <= 1) return;
        String cmd = args[0];
//        System.out.println(cmd);
        cmd = cmd.replace("@"+ Main.getBotObject().getBotUsername(),"");
        for (Command command : commands) {
//            if (command.limited()){
//                if (!limited.get(command).contains(message.getChannelId())){
//                    continue;
//                }
//            }

            if (command.getName().equalsIgnoreCase(cmd) || command.getAlias().contains(cmd.toLowerCase())) {
                command.perform(event.getMessage());
                break;
            }
        }
    }
    @SneakyThrows
    public void saveConfig(){
        new Config().save(config,file);
    }


    public Command getCommand(String str){
        return stringCommandHashMap.get(str);
    }
}
