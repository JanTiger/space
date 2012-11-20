package org.jan.common.utils.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.jan.common.utils.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * utilities for parsing XML by jdom.
 *
 * @author Jan.Wang
 * @since 1.0
 */
public class JdomXmlUtils {

    private static final String ENCODING_TYPE = "UTF-8";

    private static final NoNamespaceFilter noNamespaceFilter = new NoNamespaceFilter();

    /**
     * This method unmarshalls the content in the given xml string into an object of the specified class.
     * @param <T>
     * @param xmlString
     * @param clazz
     * @return
     */
    public static <T> T xmlToObj(String xmlString, Class<T> clazz) {
        byte[] bytes = null;
        try {
            bytes = xmlString.getBytes(ENCODING_TYPE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (null != bytes)
            return xmlToObj(new ByteArrayInputStream(bytes), clazz);
        return null;

    }

    /**
     * This method unmarshalls the content in the given inputstream into an object of the specified class.
     * @param <T>
     * @param inputStream
     * @param clazz
     * @return
     */
    public static <T> T xmlToObj(InputStream inputStream, Class<T> clazz) {
        try {
            noNamespaceFilter.setParent(XMLReaderFactory.createXMLReader());
            Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
            SAXSource source = new SAXSource(noNamespaceFilter, new InputSource(inputStream));
            return (T) unmarshaller.unmarshal(source);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method marshalls the content of the given object to an XML representation of the object and returns it as a string.
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> String objToXml(T obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objToXml(obj, outputStream);
        if(outputStream.size() > 0)
            return outputStream.toString();
        return null;
    }

    /**
     * This method marshalls the content of the given object to an XML representation of the object and writes it to the given output stream.
     * @param <T>
     * @param obj
     * @param outputStream
     */
    public static <T> void objToXml(T obj, OutputStream outputStream) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(obj.getClass()).createMarshaller();
            marshaller.marshal(obj, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs the xml doc to the specified path.
     * @param doc
     * @param pathname
     */
    public static void output(Document doc, String pathname) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(pathname);
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.output(doc, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * deriving an XML filter.
     * @author Jan.Wang
     */
    public static class NoNamespaceFilter extends XMLFilterImpl {
        public NoNamespaceFilter() {
            super();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
            super.startElement("", arg1, arg2, arg3);
        }

        @Override
        public void endElement(String arg0, String arg1, String arg2) throws SAXException {
            super.endElement("", arg1, arg2);
        }

        @Override
        public void startPrefixMapping(String prefix, String url) throws SAXException {
            // Remove the namespace, i.e. don't call startPrefixMapping for
            // parent!
        }
    }

}
