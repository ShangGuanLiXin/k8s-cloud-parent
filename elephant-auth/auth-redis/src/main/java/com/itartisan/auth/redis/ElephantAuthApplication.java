package com.itartisan.auth.redis;

import com.itartisan.common.security.annotation.EnableItartisanFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证授权中心
 */
@EnableItartisanFeignClients
@SpringCloudApplication
public class ElephantAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ElephantAuthApplication.class, args);
        System.out.println("认证授权中心启动成功!!");
    }
}
