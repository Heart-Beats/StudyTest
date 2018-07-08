package HL.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class MyCalendar {

    public void printCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int inputDate = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DATE, 1);  //date指示一个月中的某天，一个月中的第一天值为1
        int month = calendar.get(Calendar.MONTH) + 1;
        System.out.println("\t\t" + calendar.get(Calendar.YEAR) + "年" + month + "月");
        System.out.println("日\t一\t二\t三\t四\t五\t六");
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int j = 1;
        for (int i = 1; i <= maxDayOfMonth; i++) {
            while (j < firstDayOfWeek) {
                System.out.print(" \t");
                j++;
            }
            if (inputDate != i) {
                if (inputDate < 10) {
                    if (i < 10) {
                        System.out.print(" " + i + "\t");
                    } else {
                        System.out.print(i + "\t");
                    }
                } else {
                    if (inputDate - 1 != i) {//若天数不是个位数,控制选中日期的前一天的格式
                        if (i < 10) {
                            System.out.print(" " + i + "\t");
                        } else {
                            System.out.print(i + "\t");
                        }
                    } else {
                        System.out.print(i + " ");
                    }
                }
            } else {
                System.out.print("[" + i + "]" + " ");//给选中日期加标记
            }
            if ((i + j - 1) % 7 == 0) {
                System.out.println();
            }
        }
    }

    private Date getDate() throws ParseException {
        Date date = null;
        System.out.println("请选择：1.查看指定日期日历  2.查看当前日期日历");
        Scanner inputNumber = new Scanner(System.in);
        String check = inputNumber.next();
        switch (check) {
            case "1":
                System.out.println("请输入日期，格式如：1970-1-1");
                Scanner inputString = new Scanner(System.in);
                String dateStr = inputString.nextLine();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                date = df.parse(dateStr);
                //  inputString.close();
                break;
            case "2":
                date = new Date();
                break;
            default:
                System.out.println("输入有误，请重新输入！\n");
                date = getDate();  //此处递归必须将返回的值给date，否则会报空指针
/*                try {
                    throw new Exception("输入有误，请重新输入！\n");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    date = getDate();  //此处递归必须将返回的值给date，否则会报空指针
                }*/
                break;
        }
        // inputNumber.close();
        return date;
    }

    private static void recyclerPrintCalendar() {
        MyCalendar calendar = new MyCalendar();
        Date date = null;
        try {
            date = calendar.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            calendar.printCalendar(date);
        }
        label:
        while (true) {
            System.out.println("\n继续查看日历？ 是/否");
            Scanner input = new Scanner(System.in);
            String inputStr = input.next();
            switch (inputStr) {
                case "是":
                    recyclerPrintCalendar();
                    break;
                case "否":
                    break;
                default:
                    System.out.println("只可输入是或者否，请重新输入！");
/*                    try {
                        throw new Exception("只可输入是或者否，请重新输入！");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }*/
                    continue label;
            }
            //input.close();
            break;
        }
    }

    public static void main(String args[]) {
        recyclerPrintCalendar();

    }


}
