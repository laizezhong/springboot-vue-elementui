package cn.spicybar.frame.runner;

import cn.spicybar.frame.role.entity.Role;
import cn.spicybar.frame.role.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Run init after application started
 * @author dell
 * @date 2019-08-02 17:31
 */
@Order(value=2)
@Component
public class InitRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(InitRunner.class);
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("开始检测基础数据...");
            List<Role> roleList = roleRepository.findAll();
            if(roleList==null || roleList.size()==0){
                //init default common role
                roleList = new ArrayList<>();
                Role common = new Role();
                common.setName("COMMON");
                common.setDescription("普通用户");
                //init default admin role
                roleList.add(common);
                Role admin = new Role();
                admin.setName("ADMIN");
                admin.setDescription("管理员");
                roleList.add(admin);
                roleRepository.saveAll(roleList);
                logger.info("初始化基础数据成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
