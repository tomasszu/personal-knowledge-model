package tomass.tz18004;
import java.util.*;

public class Person {
    private String name;
    private String surname;
    private Integer age;
    private Map <Integer, String> knows;

    public Person() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.knows = null;
    }

    public void setKnows(Map <Integer, String> knows) {
        this.knows = knows;
    }


    public Map <Integer, String> getKnows() {
        return knows;
    }
    
    public void deleteKnow(Integer Id)
    {
        if(this.knows != null)
        {
            if(this.knows.containsKey(Id))
            {
                this.knows.remove(Id);
            }
        }        
    }
    
    
    
    
}

