package org.example.mybatis;

import org.dom4j.DocumentFactory;
import org.dom4j.ElementHandler;
import org.dom4j.io.SAXContentHandler;
import org.xml.sax.Locator;

public class CustomSAXContentHandler extends SAXContentHandler {

    private DocumentFactory myDocumentFactory;

    public CustomSAXContentHandler() {
        super();
    }

    public CustomSAXContentHandler(DocumentFactory documentFactory) {
        super(documentFactory);
        this.myDocumentFactory = documentFactory;
    }

    public CustomSAXContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler) {
        super(documentFactory, elementHandler);
        this.myDocumentFactory = documentFactory;
    }

    @Override
    public void setDocumentLocator(Locator documentLocator) {
        super.setDocumentLocator(documentLocator);
        if (myDocumentFactory instanceof CustomDocumentFactory) {
            ((CustomDocumentFactory) myDocumentFactory).setLocator(documentLocator);
        }
    }
}
