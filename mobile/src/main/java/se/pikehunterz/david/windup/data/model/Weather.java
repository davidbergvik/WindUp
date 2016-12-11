
package se.pikehunterz.david.windup.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("approvedTime")
    @Expose
    private String approvedTime;
    @SerializedName("referenceTime")
    @Expose
    private String referenceTime;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("timeSeries")
    @Expose
    private List<TimeSeries> timeSeries = null;

    /**
     * 
     * @return
     *     The approvedTime
     */
    public String getApprovedTime() {
        return approvedTime;
    }

    /**
     * 
     * @param approvedTime
     *     The approvedTime
     */
    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    /**
     * 
     * @return
     *     The referenceTime
     */
    public String getReferenceTime() {
        return referenceTime;
    }

    /**
     * 
     * @param referenceTime
     *     The referenceTime
     */
    public void setReferenceTime(String referenceTime) {
        this.referenceTime = referenceTime;
    }

    /**
     * 
     * @return
     *     The geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * 
     * @param geometry
     *     The geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * 
     * @return
     *     The timeSeries
     */
    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    /**
     * 
     * @param timeSeries
     *     The timeSeries
     */
    public void setTimeSeries(List<TimeSeries> timeSeries) {
        this.timeSeries = timeSeries;
    }

}
