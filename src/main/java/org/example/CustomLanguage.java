package org.example;

import org.sonar.api.resources.AbstractLanguage;

public class CustomLanguage extends AbstractLanguage {

    /**
     * language key, only lower case letters are supported.
     */
    public static final String LANGUAGE_KEY = "custom_lang_key";
    public static final String LANGUAGE_NAME = "custom_lang";

    public CustomLanguage() {
        super(LANGUAGE_KEY, LANGUAGE_NAME);
    }

    @Override
    public String[] getFileSuffixes() {
        return new String[]{};
    }
}
