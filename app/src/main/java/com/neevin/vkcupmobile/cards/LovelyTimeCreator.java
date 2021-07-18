package com.neevin.vkcupmobile.cards;

import java.io.ObjectInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LovelyTimeCreator {

    private static HashMap<Integer, String> months = new HashMap<>();
    {
        months.put(0, "янв");
        months.put(1, "фев");
        months.put(2, "мар");
        months.put(3, "апр");
        months.put(4, "мая");
        months.put(5, "июн");
        months.put(6, "июл");
        months.put(7, "авг");
        months.put(8, "сен");
        months.put(9, "окт");
        months.put(10, "ноя");
        months.put(11, "дек");
    }

    // Вернуть время в виде строки в красивом формате
    public static String getLovelyTime(long unixTime){
        Date publishTime = new Date(unixTime * 1000);

        long diff = ((new Date()).getTime()/1000 - unixTime);

        long sec = diff % 60;
        diff /= 60;
        long mins = diff % 60;
        diff /= 60;
        long hours = diff % 24;
        diff /= 24;
        long days = diff;

        if(days > 1){
            // DD июл/авг/сен в HH:MM
            // DD июл/авг/сен YYYY
            String month = months.get(publishTime.getMonth());

            if((new Date()).getYear() == publishTime.getYear()){
                // Если в этом году опубликована запись
                DateFormat df = new SimpleDateFormat("dd " + month + " в HH:mm");
                return df.format(publishTime);

            }
            else{
                DateFormat df = new SimpleDateFormat("dd " + month + " YYYY");
                return df.format(publishTime);
            }

        }
        else if(days == 1){
            // вчера в HH:MM
            DateFormat df = new SimpleDateFormat("вчера в HH:mm");
            return df.format(publishTime);
        }
        // else

        if(hours > 4){
            if((new Date()).getDay() == publishTime.getDay()){
                // сегодня в HH:MM
                DateFormat df = new SimpleDateFormat("сегодня в HH:mm");
                return df.format(publishTime);
            }
            else {
                // вчера в HH:MM
                DateFormat df = new SimpleDateFormat("вчера в HH:mm");
                return df.format(publishTime);
            }
        }
        else if(hours == 4){
            return "четыре часа назад";
        }
        else if(hours == 3){
            return "три часа назад";
        }
        else if(hours == 2){
            return "два часа назад";
        }
        else if(hours == 1){
            return "час назад";
        }
        // else

        // Тут проверка n минут назад или только что
        if(mins > 4){
            if(mins > 20){
                if(mins % 10 == 1){
                    return String.format("%d минуту назад", mins);
                }
                else if(mins % 10 != 0 && mins % 10 < 5){
                    return String.format("%d минуты назад", mins);
                }
            }
            return String.format("%d минут назад", mins);
        }
        else if(mins == 4){
            return "4 минуты назад";
        }
        else if(mins == 3){
            return "три минуты назад";
        }
        else if(mins == 2){
            return "две минуты назад";
        }
        else if(mins == 1){
            return "минуту назад";
        }
        return "только что";
        /*
        Должен возвращать строки вида
        только что
        минуту назад
        две минуты назад
        три минуты назад
        4 минуты назад
        n минут назад
        час назад
        два часа назад
        три часа назад
        четыре часа назад
        сегодня в HH:MM
        вчера в HH:MM
        DD июл/авг/сент в HH:MM
        DD июл/авг/сент YYYY
         */
    }
}
