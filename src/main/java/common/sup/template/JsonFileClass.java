package common.sup.template;

import org.codehaus.jackson.annotate.JsonGetter;

import java.io.Serializable;
import java.util.ArrayList;

public class JsonFileClass implements Serializable{


    private String version;
    public ArrayList<JsonParameters> parameters;



    @Override
    public String toString() {
        return this.version + "\n" + this.parameters;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    @JsonGetter("version")
    public String getVersion() {
        return this.version;
    }
    @JsonGetter("parameters")
    public void setParameters(ArrayList<JsonParameters> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<JsonParameters> setParameters() {
        return this.parameters;
    }
}
