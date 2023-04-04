package tomass.tz18004;
import java.util.*;

public class Concept {
    private String name;
    private String topic;
    private Integer id;
    private Map <Integer, String> linkedKnowledge;
    
    public Concept()
    {
        
    }

    public Concept(Integer id, String name, String topic) {
        this.id = id;
        this.name = name;
        this.topic = topic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public Map <Integer, String> getLinkedKnowledge() {
        return linkedKnowledge;
    }

    public void setLinkedKnowledge(Map <Integer, String> linkedKnowledge) {
        this.linkedKnowledge = linkedKnowledge;
    }
}
