package me.yeoc.ddosbot;

import lombok.Getter;
import lombok.SneakyThrows;
import me.yeoc.ddosbot.command.CommandManager;
import me.yeoc.ddosbot.data.IDatabase;
import me.yeoc.ddosbot.data.SaveRunnable;
import me.yeoc.ddosbot.data.impl.YamlImpl;
import me.yeoc.ddosbot.util.config.Config;
import me.yeoc.ddosbot.util.config.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.*;

public class Main {



    @Getter
    private static CommandManager commandManager;
    @Getter
    private static BotObject botObject;
    @Getter
    private static IDatabase database;

    public static void main(String[] args) {

        initConfig();

        loadConfig();

        database = new YamlImpl();
        new SaveRunnable(database).start();
        commandManager = new CommandManager();
        commandManager.init();




        System.out.println(attackURL);
        DefaultBotOptions botOptions = new DefaultBotOptions();
//        botOptions.setProxyHost("127.0.0.1");
//        botOptions.setProxyPort(7890);
//        botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);;
        DefaultBotSession defaultBotSession = new DefaultBotSession();
//        defaultBotSession.setOptions(botOptions);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(defaultBotSession.getClass());
            botObject = new BotObject(botOptions);

            telegramBotsApi.registerBot(botObject);
            System.out.println("添加完毕");

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public static void loadConfig() {
        pointPerAttack = new HashMap<>();
        //TODO
        attackURL = config.getString("attackURL");

        successAttackKeyWords = new ArrayList<>();
        for (String keyWords : config.getStringList("successAttackKeyWords")) {
            successAttackKeyWords.add(keyWords.toLowerCase());
        }

        allowMethod = new HashMap<>();
        for (String keyWords : config.getStringList("allowType")) {
            String[] split = keyWords.split(":");
            allowMethod.put(split[0].toLowerCase(),split[1]);
        }

        defaultPoint = config.getInt("defaultPoint",0);

        adminUID = config.getLongList("adminUID");
        //TODO

        attackAble = config.getBoolean("attackAble");
        reason = config.getString("reason");
        helpMessage = config.getString("helpMessage");
        aboutMessage = config.getString("infoMessage");

        bot_name = config.getString("bot_username");
        bot_token = config.getString("bot_token");
    }

    @SneakyThrows
    public static void saveConfig(){
        new Config().save(config,file);
    }

    @Getter
    private static Configuration config;
    private static File file;
    @Getter
    private static String helpMessage;
    @Getter
    private static String aboutMessage;

    @Getter
    private static HashMap<String,Integer> pointPerAttack;

    @Getter
    private static String attackURL;
    @Getter
    private static List<String> successAttackKeyWords;
    @Getter
    private static HashMap<String,String> allowMethod;
    @Getter
    private static int defaultPoint;
    @Getter
    private static List<Long> adminUID;
    @Getter
    private static boolean attackAble;
    @Getter
    private static String reason;
    @Getter
    private static String bot_name,bot_token;
    @SneakyThrows
    public static void initConfig(){
        file = new File("config.yml");
        if (!file.exists()) file.createNewFile();
        config = new Config().load(file);
    }
}
