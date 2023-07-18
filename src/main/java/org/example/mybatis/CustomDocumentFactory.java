package org.example.mybatis;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xml.sax.Locator;

public class CustomDocumentFactory extends DocumentFactory {

    private Locator locator;

    public CustomDocumentFactory() {
        super();
    }

    public void setLocator(Locator locator) {
        this.locator = locator;
    }

    @Override
    public Element createElement(QName qname) {
        CustomDefaultElement element = new CustomDefaultElement(qname);
        if (locator != null) {
            element.setLineNumber(locator.getLineNumber());
        }
        return element;
    }
}
