package me.yeoc.ddosbot.data;

import me.yeoc.ddosbot.object.CDK;
import me.yeoc.ddosbot.object.User;

public interface IDatabase {

    boolean existUser(Long id);
    boolean registerUser(Long id);
    User getUser(Long id);

    boolean existCDK(String cdk);

    CDK getCDK(String cdk);


    boolean setCDK(CDK cdk);

    boolean saveUser(User user);

    default void saveData(){};

}
