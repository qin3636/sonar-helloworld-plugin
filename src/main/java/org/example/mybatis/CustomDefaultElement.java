package org.example.mybatis;

import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

public class CustomDefaultElement extends DefaultElement {

    private int lineNumber = 0;

    public CustomDefaultElement(String name) {
        super(name);
    }

    public CustomDefaultElement(QName qname) {
        super(qname);
    }

    public CustomDefaultElement(QName qname, int attributeCount) {
        super(qname, attributeCount);
    }

    public CustomDefaultElement(String name, Namespace namespace) {
        super(name, namespace);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
