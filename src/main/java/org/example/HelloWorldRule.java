package org.example;

import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;

public class HelloWorldRule implements RulesDefinition {

    public static final String REPOSITORY_KEY = "word_check";
    public static final String RULE_KEY = "word_hello_world_check_issue";
    public static final String RULE_NAME = "hello-world规则";
    public static final String RULE_DESCRIPTION = "代码中不能包含hello-world";
    public static final String RULE_TAG1 = "hello-world";
    public static final String RULE_TAG2 = "hello-world-check";

    @Override
    public void define(Context context) {
        // 定义规则存储
        NewRepository newRepository = context.createRepository(REPOSITORY_KEY, "java");

        // 生成规则
        newRepository.createRule(RULE_KEY)
                .setName(RULE_NAME)
                .setHtmlDescription(RULE_DESCRIPTION)
                .setTags(RULE_TAG1, RULE_TAG2)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.BUG)
                .setSeverity(Severity.MAJOR);

        newRepository.done();
    }
}
