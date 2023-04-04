
package tomass.tz18004;


public class Link {
    private Integer subject;
    private String meaning;
    private Integer object;

    public Link(Integer subject, String meaning, Integer object) {
        this.subject = subject;
        this.meaning = meaning;
        this.object = object;
    }

    public Link()
    {
        
    }
    
    public Integer getSubject() {
        return subject;
    }


    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public String getMeaning() {
        return meaning;
    }


    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Integer getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }    
}
