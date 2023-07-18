package org.example.mybatis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBatisSensor implements Sensor {

    private static final Logger LOGGER = Loggers.get(MyBatisSensor.class);

    @Override
    public void describe(SensorDescriptor descriptor) {

    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fileSystem = context.fileSystem();
        FilePredicates predicates = fileSystem.predicates();
        Iterable<InputFile> files = fileSystem.inputFiles(predicates.hasLanguage("xml"));
        for (InputFile inputFile : files) {
            String path = inputFile.uri().getPath();
            File file = new File(path);
            SAXReader saxReader = new SAXReader();

            // 忽略DTD校验
            saxReader.setValidation(false);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
            EntityResolver customEntityResolver = (publicId, systemId) -> new InputSource(byteArrayInputStream);
            saxReader.setEntityResolver(customEntityResolver);

            try {
                Document document = saxReader.read(file);
                // 获取根节点，判断是否是mybatis文件
                Element rootElement = document.getRootElement();
                if (!"mapper".equalsIgnoreCase(rootElement.getName())) {
                    continue;
                }
                Iterator rootElementIterator = rootElement.elementIterator();
                while (rootElementIterator.hasNext()) {
                    Object obj = rootElementIterator.next();
                    if (obj instanceof Element) {
                        Element element = (Element) obj;
                        if ("select".equalsIgnoreCase(element.getName())) {
                            Object data = element.getData();
                            String sql = data instanceof String ? (String) data : null;
                            LOGGER.info("checking the sql " + sql);
                            if (sqlWithUrCheck(sql)) {
                                LOGGER.warn(sql + " has with ur");
                                saveWithUrIssue(context, inputFile, sql);
                            }
                        }
                    }
                }
            } catch (DocumentException e) {
                LOGGER.error("parse document error", e);
            }
        }
    }

    private boolean sqlWithUrCheck(String sql) {
        if (sql == null) {
            return false;
        }
        String tmpSql = sql;
        tmpSql = tmpSql.replaceAll("[\\t\\n\\r]", " ");
        tmpSql = tmpSql.toLowerCase();
        LOGGER.info("transformed sql: " + tmpSql);
        String exp = ".*\\swith\\s+ur.*";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(tmpSql);
        return matcher.matches();
    }

    private void saveWithUrIssue(SensorContext sensorContext, InputFile file, String sql) {
        NewIssue newIssue = sensorContext.newIssue();
        newIssue.forRule(RuleKey.of(MyBatisRule.REPOSITORY_KEY, MyBatisRule.RULE_KEY));
        NewIssueLocation newIssueLocation = newIssue.newLocation();
        newIssueLocation.on(file).message("using with ur in sql [" + sql + "] might be unsafe.");
        newIssue.at(newIssueLocation);
        newIssue.save();
    }
}
