package me.yeoc.ddosbot.data.impl;

import lombok.SneakyThrows;
import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.data.IDatabase;
import me.yeoc.ddosbot.object.CDK;
import me.yeoc.ddosbot.object.User;
import me.yeoc.ddosbot.util.config.Config;
import me.yeoc.ddosbot.util.config.Configuration;

import java.io.File;
import java.io.IOException;

public class YamlImpl implements IDatabase {

    Configuration configuration;
    File file;
    @SneakyThrows
    public YamlImpl(){
        file = new File("database.yml");
        if (!file.exists()) file.createNewFile();
        configuration = new Config().load(file);


    }

    @Override
    public boolean existUser(Long id) {
        return configuration.get("user."+id) != null;
    }

    @Override
    public boolean registerUser(Long id) {
        if (existUser(id)){
            return false;
        }
        configuration.set("user."+id+".point",Main.getDefaultPoint());
        configuration.set("user."+id+".expireTime",System.currentTimeMillis());
        return true;
    }

    @Override
    public User getUser(Long id) {
        User user = new User(id);
        user.setPoint(configuration.getLong("user."+user.getId()+".point",Main.getDefaultPoint()));
        user.setExpireTime(configuration.getLong("user."+user.getId()+".expireTime",System.currentTimeMillis()));
        return user;
    }

    @Override
    public boolean existCDK(String cdk) {
        return configuration.get("cdk."+cdk) != null;
    }

    @Override
    public CDK getCDK(String cdk) {
        Configuration section = configuration.getSection("cdk." + cdk);
        return new CDK(cdk,section.getString("type"),section.getLong("content"),section.getBoolean("used"));
    }


    @Override
    public boolean setCDK(CDK cdk) {
        configuration.set("cdk."+cdk.getText()+".type",cdk.getType());
        configuration.set("cdk."+cdk.getText()+".content",cdk.getContent());
        configuration.set("cdk."+cdk.getText()+".used",cdk.isUsed());
        saveData();
        return true;
    }

    @Override
    public boolean saveUser(User user) {
        try {
            configuration.set("user."+user.getId()+".point",user.getPoint());
            configuration.set("user."+user.getId()+".expireTime",user.getExpireTime());
            saveData();
            return true;
        }catch (Exception ignore){
            return false;
        }
    }

    @Override
    public void saveData() {
        try {
            new Config().save(configuration,file);
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
