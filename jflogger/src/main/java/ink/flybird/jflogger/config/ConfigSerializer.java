package ink.flybird.jflogger.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class ConfigSerializer {

    public static final String STAND_CODE = "jFLogger";
    public static final String STAND_VER = "1.x";



    private final Document doc;
    private final Element config, colorScheme;
    private final String filePath;

    public ConfigSerializer(String filePath) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        var root = attachRoot();
        this.filePath = filePath;


        config = doc.createElement("Config");
        root.appendChild(config);

        colorScheme = doc.createElement("ColorScheme");
        root.appendChild(colorScheme);
    }

    private Element attachRoot() {
        Element root = doc.createElement("FLogger");
        root.setAttribute("version", STAND_VER);
        root.setAttribute("controller", STAND_CODE);

        doc.appendChild(root);
        return root;
    }

    public void serialize(Map<String, String> colorMap) {
        for(var item : colorMap.keySet()) {
            var value = colorMap.get(item);

            var logColor = doc.createElement("LogColor");
            logColor.setAttribute("level", item);
            logColor.appendChild(doc.createTextNode(value));

            colorScheme.appendChild(logColor);
        }
    }

    public void serialize(ConfigBuilder builder) {
        var clazz = builder.getClass();
        var fields = clazz.getFields();

        for(var fi : fields) {
            try {
                var ans = fi.getDeclaredAnnotations();
                boolean isDead = false;
                for(var an : ans) {
                    if (an instanceof DoNotSerialize) {
                        isDead = true;
                        break;
                    }
                }
                if(isDead) continue;

                var fName = fi.getName();
                var fType = fi.getType().toString();
                if(fType.contains("String")) {
                    fType = "string";
                }
                var fValue = fi.get(builder);

                var configItem = doc.createElement("ConfigItem");
                configItem.setAttribute("key", fName);
                configItem.setAttribute("type", fType);
                configItem.appendChild(doc.createTextNode(fValue.toString()));

                config.appendChild(configItem);
            } catch (Exception ignored) {

            }
        }
    }

    public void save() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); // 启用缩进
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // 设置缩进空格数

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
