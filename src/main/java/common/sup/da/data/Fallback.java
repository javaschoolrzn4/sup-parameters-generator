package common.sup.da.data;

import org.codehaus.jackson.annotate.JsonGetter;

import java.io.Serializable;
import java.util.List;

public class Fallback implements Serializable{

    public List<Parameter> parameters;

    public List<Parameter> getParameters() {
        return parameters;
    }
    @JsonGetter("parameters")
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
