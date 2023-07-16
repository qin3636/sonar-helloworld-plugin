package org.example.mybatis;

import org.sonar.api.rule.Severity;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.xml.Xml;

public class MyBatisRule implements RulesDefinition {

    public static final String REPOSITORY_KEY = "mybatis";
    public static final String RULE_KEY = "mybatis_key";
    public static final String RULE_NAME = "mybatis-xml-rule";
    public static final String RULE_DESCRIPTION = "mybatis xml文件检查";
    public static final String RULE_TAG = "mybatis-check";

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, Xml.KEY);
        repository.createRule(RULE_KEY)
                .setName(RULE_NAME)
                .setHtmlDescription(RULE_DESCRIPTION)
                .setTags(RULE_TAG)
                .setType(RuleType.CODE_SMELL)
                .setSeverity(Severity.MINOR);
        repository.done();
    }
}
