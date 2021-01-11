package cn.sunhomo.test;

import cn.sunhomo.club.domain.SunActivity;
import cn.sunhomo.club.domain.SunBattle;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.PropertyAccessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nickel Fang
 * @date: 2020/10/13 15:33
 */
public class PropertyAccessorTest {
    public static void main(String[] args) {
//        SunActivity activity = new SunActivity();
//        BeanWrapperImpl accessor = new BeanWrapperImpl(activity);
//
//        accessor.setPropertyValue("activityId", 1);
//        accessor.setPropertyValue("params[beginTime]", "2020-10-13");
//        System.out.println(activity.getParams());
//
//        System.out.println(LocalDateTime.now().isBefore(LocalDateTime.parse("2020-10-20 22:43:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//        System.out.println(LocalDateTime.parse("2020-10-20 22:41:00"));

//        Integer a = 1;
//        Integer b = 1;
//        System.out.println(a == b);

        List<SunBattle> battles = new ArrayList<>();
        for(SunBattle battle: battles){
            System.out.println("11");
        }
    }

}
