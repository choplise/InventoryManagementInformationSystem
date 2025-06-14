package org.shixuan.inventorymanagementinformationsystem;

import org.junit.jupiter.api.Test;
import org.shixuan.inventory.InventoryManagementInformationSystemApplication;
import org.shixuan.inventory.dto.LoginRequest;
import org.shixuan.inventory.dto.LoginResponse;
import org.shixuan.inventory.service.SysUserService;
import org.shixuan.inventory.service.impl.SysUserServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = InventoryManagementInformationSystemApplication.class)
class InventoryManagementInformationSystemApplicationTests {


    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(  passwordEncoder.encode("123456"));
    }

}
