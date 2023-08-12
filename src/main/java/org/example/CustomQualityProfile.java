package org.example;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

public class CustomQualityProfile implements BuiltInQualityProfilesDefinition {

    public static final String QUALITY_PROFILE_NAME = "CustomQualityProfile";

    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(QUALITY_PROFILE_NAME, CustomLanguage.LANGUAGE_KEY);
        profile.setDefault(false);
        profile.activateRule(HelloWorldRule.REPOSITORY_KEY, HelloWorldRule.RULE_KEY);
        profile.done();
    }
}
