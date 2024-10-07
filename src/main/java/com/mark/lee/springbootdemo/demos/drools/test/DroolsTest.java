package com.mark.lee.springbootdemo.demos.drools.test;

import com.mark.lee.springbootdemo.demos.drools.entity.Order;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void test(){
        //first 获取服务
        KieServices kieServices = KieServices.Factory.get();
        //second 通过服务器获取容器
        KieContainer container = kieServices.getKieClasspathContainer();
        //third 通过容器获取kieSession，有kieSession和规则引擎交互
        KieSession session = container.newKieSession();
        //forth 事实对象
        Order order = new Order();
        order.setAmount(234);//订单金额
        //第四步:插入工作内存，将数据插入工作引擎，规则引擎根据提供的数据进行规则匹配
        session.insert(order);
        //执行规则引擎:执行所有规则
        session.fireAllRules();
        //关闭session
        session.dispose();
        System.out.println(order);

    }

}
