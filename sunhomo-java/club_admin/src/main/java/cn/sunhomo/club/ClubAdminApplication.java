package cn.sunhomo.club;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Nickel Fang
 * @date: 2020/9/25 16:09
 */
@MapperScan("cn.sunhomo.**.mapper")
@SpringBootApplication
public class ClubAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClubAdminApplication.class,args);
    }
}
