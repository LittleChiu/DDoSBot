package me.yeoc.ddosbot.object;

import lombok.Getter;
import lombok.Setter;
import me.yeoc.ddosbot.Main;
import me.yeoc.ddosbot.util.HttpClientUtil;

@Getter
public class User {

    private final Long id;

    @Setter
    private Long point = 0L;

    @Setter
    private Long expireTime = 0L;


    public User(Long id){
        this.id = id;
    }

    public void addPoint(int n){
        point+= n;
    }

    public void addExpireTime(int millionSec){
        if (expireTime < System.currentTimeMillis()){
            expireTime = System.currentTimeMillis() + millionSec;
        }else {
            expireTime += millionSec;
        }
    }

    public boolean canAttack(String type){
        if (expireTime >= System.currentTimeMillis()){
            return true;
        }
        return Main.getPointPerAttack().getOrDefault(type, 5) <= point;
    }

    public boolean attack(String type,String ip,int port){
        if (!canAttack(type)) {return false;}

        boolean success = false;

        String response = Main.getAttackURL()
                .replace("$type$", Main.getAllowMethod().get(type.toLowerCase()))
                .replace("$host$", ip)
                .replace("$port$", String.valueOf(port));

        String s = HttpClientUtil.doGet(response);
        for (String word : Main.getSuccessAttackKeyWords()) {
            if (s.toLowerCase().contains(word)) {
                success = true;
                break;
            }
        }


        if (success){
            if (expireTime < System.currentTimeMillis()){
                point -= Main.getPointPerAttack().getOrDefault(type, 5);
            }
            return true;
        }


        return false;
    }


}
