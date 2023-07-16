package org.example;

import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HelloWorldSensor implements Sensor {

    private static final Logger LOGGER = Loggers.get(HelloWorldSensor.class);

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor.name("不允许在代码文件中使用单词hello-world");
    }

    @Override
    public void execute(SensorContext sensorContext) {
        FileSystem fileSystem = sensorContext.fileSystem();
        FilePredicates predicates = fileSystem.predicates();
        // 获取目录所有文件
        List<InputFile> files = StreamSupport.stream(
                        fileSystem.inputFiles(predicates.all()).spliterator(), false)
                .collect(Collectors.toList());
        if (files.isEmpty()) {
            return;
        }

        for (InputFile file : files) {
            try (
                    InputStream inputStream = file.inputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
            ) {
                String line;
                while ((line = lineNumberReader.readLine()) != null) {
                    // 若发现问题则保存
                    if (line.contains("hello-world")) {
                        int lineNumber = lineNumberReader.getLineNumber();
                        LOGGER.warn("found issue at line" + lineNumber);
                        NewIssue newIssue = sensorContext.newIssue();
                        newIssue.forRule(RuleKey.of(HelloWorldRule.REPOSITORY_KEY, HelloWorldRule.RULE_KEY));
                        NewIssueLocation newIssueLocation = newIssue.newLocation();
                        newIssueLocation.on(file).at(file.selectLine(lineNumber)).message(HelloWorldRule.RULE_DESCRIPTION);
                        newIssue.at(newIssueLocation);
                        newIssue.save();
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Error occurred while processing file " + file.filename(), e);
            }
        }
    }
}
