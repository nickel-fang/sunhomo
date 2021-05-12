package cn.sunhomo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Nickel Fang
 * @date: 2020/9/25 16:09
 */
@MapperScan("cn.sunhomo.**.mapper")
@SpringBootApplication
public class ClubApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClubApiApplication.class,args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
