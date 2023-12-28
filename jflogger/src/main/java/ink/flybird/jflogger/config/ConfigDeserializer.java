package ink.flybird.jflogger.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.stream.Stream;

public class ConfigDeserializer {

    private final String name;
    private final Document doc;
    private final Element config;
    private final Element colorScheme;

    public ConfigDeserializer(String name) throws Exception {
        this.name = name;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        var stream = new FileInputStream(name);
        var str = new String(stream.readAllBytes());
        stream.close();
        var inputSource = new InputSource(new StringReader(str));

        doc = builder.parse(inputSource);
        
        config = (Element) doc.getElementsByTagName("Config").item(0);
        colorScheme = (Element) doc.getElementsByTagName("ColorScheme").item(0);
    }

    public ConfigDeserializer(InputStream file) throws Exception {
        this.name = file.toString();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        var str = new String(file.readAllBytes());
        file.close();
        var inputSource = new InputSource(new StringReader(str));

        doc = builder.parse(inputSource);

        config = (Element) doc.getElementsByTagName("Config").item(0);
        colorScheme = (Element) doc.getElementsByTagName("ColorScheme").item(0);
    }

    public void deserialize(ConfigBuilder builder) {
        var clazz = builder.getClass();
        var fields = clazz.getFields();

        var map = new HashMap<String, Object>();
        var colorMap = new HashMap<String, String>();

        /// Fuck You Java Dom
        /// Why the children map and attribute map lack support to foreach???!
        /// Aren't Updating Every Time?

        int length = config.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            var item = config.getChildNodes().item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getNodeName().equals("ConfigItem")) {
                var name = item.getAttributes().getNamedItem("key").getNodeValue();
                var typed = item.getAttributes().getNamedItem("type").getNodeValue();
                var typedValue = switch (typed) {
                    case "int" -> Integer.parseInt(item.getTextContent());
                    case "double" -> Double.parseDouble(item.getTextContent());
                    case "float" -> Float.parseFloat(item.getTextContent());
                    case "boolean" -> Boolean.parseBoolean(item.getTextContent());
                    default -> item.getTextContent();
                };

                map.put(name, typedValue);
            }
        }

        int cLength = colorScheme.getChildNodes().getLength();
        for (int i = 0; i < cLength; i++) {
            var item = colorScheme.getChildNodes().item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && item.getNodeName().equals("LogColor")) {
                var name = item.getAttributes().getNamedItem("level").getNodeValue();
                colorMap.put(name, item.getTextContent());
            }
        }

        for(var fid : fields) {
            if(fid.getName().equals("colorMap")) {
                try {
                    fid.set(builder, colorMap);
                } catch (Exception ignore) {

                }
            } else {
                if(!map.containsKey(fid.getName())) continue;
                var typedValue = map.get(fid.getName());
                try {
                    fid.set(builder, typedValue);
                } catch (Exception ignore) {

                }
            }
        }
    }
}
