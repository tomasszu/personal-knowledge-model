package tomass.tz18004;
import java.util.*;

public class Knowledge {
    private String name;
    private KnowledgeType type;
    private Double quotient;
    private String topic;
    private String description;
    private Integer id;
    private Map <Integer, String> linkedKnowledge;
    
    public Knowledge()
    {
        
    }

    public Knowledge(Integer id, String name, String topic, String description) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.linkedKnowledge = null;
        this.quotient = null;
        this.type = null;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnowledgeType getType() {
        return type;
    }

    public void setType(KnowledgeType type) {
        this.type = type;
    }
    
    public void setType() {
        if(this.quotient < 0.05) this.type = KnowledgeType.EXPLICIT;
        else if(this.quotient < 0.96) this.type = KnowledgeType.MIXED;
        else this.type = KnowledgeType.TACIT;
    }
    
    public Double getQuotient() {
        return quotient;
    }

    public void setQuotient(Vector <Link> linkList) {
        Double referenceNr = 5.00;
        Double quotient = 0.00;
        if(linkList.size() < referenceNr)
        {
            quotient = (Double)(linkList.size()/ referenceNr);
            if(!linkList.isEmpty())
            {
                for(Integer i = 0; i < linkList.size(); i++)
                {
                    if(linkList.get(i).getSubject().equals(0))
                    {quotient = quotient + 0.20; break;}
                }
            }
        }
        else
        {
            quotient = 0.90;
            if(!linkList.isEmpty())
            {
                for(Integer i = 0; i < linkList.size(); i++)
                {
                    if(linkList.get(i).getObject().equals(0))
                    {quotient = quotient + 0.05; break;}
                }
            }
        }
        quotient = Math.round(quotient * 100.0) / 100.0;
        this.quotient = quotient;
        
    }
    
    public void setQuotient(Double quotient)
    {
        this.quotient = quotient;
    }

    public String getTopic() {
        return topic;
    }

    
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map <Integer, String> getLinkedKnowledge() {
        return linkedKnowledge;
    }

    public void setLinkedKnowledge(Map <Integer, String> linkedKnowledge) {
        this.linkedKnowledge = linkedKnowledge;
    }
    
        
}
