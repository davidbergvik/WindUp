
package se.pikehunterz.david.windup.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("levelType")
    @Expose
    private String levelType;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("values")
    @Expose
    private List<Double> values = null;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The levelType
     */
    public String getLevelType() {
        return levelType;
    }

    /**
     * 
     * @param levelType
     *     The levelType
     */
    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    /**
     * 
     * @return
     *     The level
     */
    public int getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * @return
     *     The values
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * 
     * @param values
     *     The values
     */
    public void setValues(List<Double> values) {
        this.values = values;
    }

}
