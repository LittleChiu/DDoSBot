package me.yeoc.ddosbot.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class CDK {

    final String text;

    final String type;

    final long content;

    boolean used;


    public boolean canUse(){
        return !used;
    }
}
