package org.example;

import org.example.mybatis.MyBatisQualityProfile;
import org.example.mybatis.MyBatisRule;
import org.example.mybatis.MyBatisSensor;
import org.sonar.api.Plugin;

public class HelloWorldPlugin implements Plugin {
    @Override
    public void define(Context context) {
//        // 注册语言
//        context.addExtension(CustomLanguage.class);

        // 注册规则
        context.addExtension(HelloWorldRule.class);
        context.addExtension(CustomQualityProfile.class);
        context.addExtension(MyBatisRule.class);
        context.addExtension(MyBatisQualityProfile.class);

        // 注册传感器
        context.addExtension(HelloWorldSensor.class);
        context.addExtension(MyBatisSensor.class);
    }
}
