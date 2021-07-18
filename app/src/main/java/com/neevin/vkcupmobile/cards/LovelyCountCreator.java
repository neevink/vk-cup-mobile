package com.neevin.vkcupmobile.cards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LovelyCountCreator {

    // Вернуть количество в виде строки в красивом формате
    public static String getLovelyCount(int count){
        if(count >= 1000000){
            return count/1000000 + "M";
        }
        if(count >= 1000){
            return count/1000 + "K";
        }
        return "" + count;
    }
}
