
package se.pikehunterz.david.windup.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSeries {

    @SerializedName("validTime")
    @Expose
    private String validTime;
    @SerializedName("parameters")
    @Expose
    private List<Parameter> parameters = null;

    /**
     * 
     * @return
     *     The validTime
     */
    public String getValidTime() {
        return validTime;
    }

    /**
     * 
     * @param validTime
     *     The validTime
     */
    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    /**
     * 
     * @return
     *     The parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * 
     * @param parameters
     *     The parameters
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
