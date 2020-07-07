package sample;

public class Region {
    private String regionName;
    private Integer regionCount;

    public Region(String regionName, Integer regionCount) {
        this.regionName = regionName;
        this.regionCount = regionCount;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(Integer regionCount) {
        this.regionCount = regionCount;
    }
}
