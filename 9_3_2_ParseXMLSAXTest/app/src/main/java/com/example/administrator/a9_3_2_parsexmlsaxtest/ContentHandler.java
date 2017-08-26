package com.example.administrator.a9_3_2_parsexmlsaxtest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 17-8-10.
 */

public class ContentHandler extends DefaultHandler {
    private static final String TAG = "ContentHandler";

    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;

    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodeName)) {
            id.append(ch, 0, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, 0, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, 0, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if ("app".equals(localName)) {
            Log.d(TAG, "endElement: id is " + id.toString().trim());
            Log.d(TAG, "endElement: name is " + name.toString().trim());
            Log.d(TAG, "endElement: version is " + version.toString().trim());

            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
//
//        builder.append("getRequest count = ");
//        builder.append(String.valueOf(MainActivity.get_count));
//        builder.append("\r\nid = ");
//        builder.append(id);
//        builder.append("\r\nname = ");
//        builder.append(name);
//        builder.append("\r\nversion = ");
//        builder.append(version);
//        showString(builder.toString(), textViewGet);

        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

    }
}
