package common.sup.da.data;

import org.codehaus.jackson.annotate.JsonGetter;

import java.io.Serializable;
import java.util.List;

public class Parameter implements Serializable{
    public String getName() {
        return name;
    }
    @JsonGetter("name")
    public void setName(String name) {
        this.name = name;
    }

    public List<Path> getPath() {
        return path;
    }
    @JsonGetter("path")
    public void setPath(List<Path> path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    private String name;
    List<Path> path;
    String type;
    List<String> values;
}
