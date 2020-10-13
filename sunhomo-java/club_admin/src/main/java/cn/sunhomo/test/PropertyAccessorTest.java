package cn.sunhomo.test;

import cn.sunhomo.club.domain.SunActivity;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.PropertyAccessor;

/**
 * @author: Nickel Fang
 * @date: 2020/10/13 15:33
 */
public class PropertyAccessorTest {
    public static void main(String[] args) {
        SunActivity activity = new SunActivity();
        BeanWrapperImpl accessor = new BeanWrapperImpl(activity);

        accessor.setPropertyValue("activityId", 1);
        accessor.setPropertyValue("params[beginTime]","2020-10-13");
        System.out.println(activity.getParams());
    }

}
