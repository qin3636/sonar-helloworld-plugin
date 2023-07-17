package org.example.mybatis;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

public class MyBatisQualityProfile implements BuiltInQualityProfilesDefinition {

    public static final String QUALITY_PROFILE_NAME = "MybatisQualityProfile";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(QUALITY_PROFILE_NAME, "xml");
        profile.setDefault(false);
        profile.activateRule(MyBatisRule.REPOSITORY_KEY, MyBatisRule.RULE_KEY);
    }
}
