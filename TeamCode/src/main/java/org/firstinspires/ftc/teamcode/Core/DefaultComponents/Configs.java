package org.firstinspires.ftc.teamcode.Core.DefaultComponents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class Configs {

    public class JsonFileWriter {
        public static void main(String[] args) throws IOException {
            public void saveValue(String name, String value){
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode jsonNode = objectMapper.createObjectNode();
                jsonNode.put("name", name);
                jsonNode.put("value", value);
                objectMapper.writeValue(new File("configs.json"), jsonNode);
            }

        }
    }
    public class JsonFileReader {
        public static void main(String[] args) throws IOException {
            public String loadValue(String name){
                JsonNode jsonNode = objectMapper.readTree(new File("configs.json"));
                String name = jsonNode.get("name").asText();
            }
        }
    }
}
