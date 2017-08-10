package com.hzu.jpg.commonwork.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/3/5.
 */

public class TimeUtil {

    static  String[] weeks={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};

    public static String getWeek(int i){
        Calendar calendar=Calendar.getInstance();
        int size=weeks.length;
        return weeks[(size+calendar.get(Calendar.DAY_OF_WEEK)+i-1)%size];
    }

    public static int getWeek(String year,String month){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,1);
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    public static String getWeek(String year,String month,String day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
        return weeks[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }


    public static String getDateYM(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        return format.format(new Date());
    }

    public static String getDateYM(long l){
        Date date=new Date(l);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    public static String getDateYM(String date,int i){
        String sMonth=date.substring(5);
        int month=Integer.parseInt(sMonth);
        String sYear=date.substring(0,4);
        if(month+i<=0){
            month+=12+i;
            int year=Integer.parseInt(sYear);
            year-=1;
            if(month<10){
                String s_month="0"+month;
                return year+"-"+s_month;
            }
            return year+"-"+month;
        }
        if(month+i>12){
            month=(month+i)%12;
            int year=Integer.parseInt(sYear);
            year+=1;
            if(month<10){
                String s_month="0"+month;
                return year+"-"+s_month;
            }
            return year+"-"+month;
        }
        month+=i;
        if(month<10){
            String s_month="0"+month;
            return sYear+"-"+s_month;
        }
        return sYear+"-"+month;
    }

    public static  String getDateYMD(int i){
        Date date=new Date();
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,i);
        date=calendar.getTime();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static  String getDateYMD(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public  static String getMonth(){
        SimpleDateFormat format=new SimpleDateFormat("MM");
        String mm=format.format(new Date());
        if(mm.startsWith("0")){
            mm=mm.substring(1);
        }
        return mm;
    }
    public  static String getDay(){
        SimpleDateFormat format=new SimpleDateFormat("dd");
        String dd=format.format(new Date());
        if(dd.startsWith("0")){
            dd=dd.substring(1);
        }
        return dd;
    }

    public static double Time2Double(int hour,int minute){
        DecimalFormat format=new DecimalFormat("#.00");
        double m=minute;
        double result=m/60.0+hour;
        return Double.parseDouble(format.format(result));
    }

    public static String getWeek(int year,int month,int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month, day);
        return weeks[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static int getDaysOfMonth(int month){
        switch (month){
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if(RunNian(Integer.parseInt(getDateYM().substring(0,4))))
                    return 29;
                return 28;
            default:
                return 31;

        }
    }
    private static boolean RunNian(int year){
        if(year%4==0){
            if(year%100==0&&year%400!=0){
                return false;
            }
            return true;
        }
        return false;
    }
    public static int getDaysOfMonth(int year,int month){
        switch (month){
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if(RunNian(year))
                    return 29;
                return 28;
            default:
                return 31;

        }
    }

    public boolean inPeriod(String period){
        //3月4日-4月3日
        String[] split=period.split("-");
        String month=getMonth();//3月
        String firstMonth=split[0].substring(0,split[0].indexOf("月"));
        int firstDay=Integer.parseInt(split[0].substring(split[0].indexOf("月"+1,split[0].indexOf("日"))));
        int day=Integer.parseInt(getDay());
        if(month.equals(firstMonth)){
            if (day>firstDay)
                return true;
            else
                return false;
        }
        String secondMonth=split[1].substring(0,split[1].indexOf("月"));
        int secondDay=Integer.parseInt(split[1].substring(split[1].indexOf("月"+1,split[1].indexOf("日"))));
        if (month.equals(secondMonth)&&day<secondDay){
                return true;
        }
        return false;
    }

    public boolean inPeriod(String period,String date_ymd){
        //3月4日-4月3日
        String[] split=period.split("-");
        String month=date_ymd.substring(date_ymd.indexOf("年")+1,date_ymd.indexOf("月"));
        if(month.startsWith("0"))
            month=month.substring(1);
        String firstMonth=split[0].substring(0,split[0].indexOf("月"));
        int firstDay=Integer.parseInt(split[0].substring(split[0].indexOf("月"+1,split[0].indexOf("日"))));
        int day=Integer.parseInt(date_ymd.substring(date_ymd.indexOf("月")+1,date_ymd.indexOf("日")));
        if(month.equals(firstMonth)){
            if (day>firstDay)
                return true;
            else
                return false;
        }
        String secondMonth=split[1].substring(0,split[1].indexOf("月"));
        int secondDay=Integer.parseInt(split[1].substring(split[1].indexOf("月"+1,split[1].indexOf("日"))));
        if (month.equals(secondMonth)&&day<secondDay){
            return true;
        }
        return false;
    }

    public String BuildWorkPeriod(String setting){
        StringBuilder sb=new StringBuilder();
        int settingDay=Integer.parseInt(setting.substring(0,setting.indexOf("号")));
        int day=Integer.parseInt(getDay());
        int month=Integer.parseInt(getMonth());
        if (day<settingDay){
            int lastMonth=(12+month-1-1)%12+1;
            sb.append(lastMonth).append("月").append(settingDay).append("日-").append(month).append("月").append(settingDay-1).append("日");
        }else{
            if(settingDay==1){
                int days=getDaysOfMonth(month);
                sb.append(month).append("月1日-").append(month).append("月").append(days).append("日");
            }else{
                int nextMonth=(month+1-1)/12+1;
                sb.append(month).append("月").append(settingDay).append("日-").append(nextMonth).append("月").append(settingDay-1).append("日");
            }
        }
        return sb.toString();
    }
}
