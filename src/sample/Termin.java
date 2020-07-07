package sample;

/**
 * Class to get object term from db
 */

public class Termin {
    private Integer termId;
    private String termName;
    private String termDescription;
    private String termParent;
    private String termRegion;
    private Integer termRegionId;

    /**
     * Constructor to put information into full termins table
     * @param termName
     * @param termDescription
     * @param termParent
     * @param termRegion
     */

    public Termin(String termName, String termDescription, String termParent, String termRegion) {
        this.termName = termName;
        this.termDescription = termDescription;
        this.termParent = termParent;
        this.termRegion = termRegion;
    }

    /**
     * Constructor for only term name
     * @param termName
     */

    public Termin(String termName) {
        this.termName = termName;
    }

    /**
     * Constructor to out table without name
     * @param termDescription
     * @param termParent
     * @param termRegion
     */

    public Termin(String termDescription, String termParent, String termRegion) {
        this.termDescription = termDescription;
        this.termParent = termParent;
        this.termRegion = termRegion;
    }

    /**
     * All getters and setters
     */

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public String getTermParent() {
        return termParent;
    }

    public void setTermParent(String termParent) {
        this.termParent = termParent;
    }

    public String getTermRegion() {
        return termRegion;
    }

    public void setTermRegion(String termRegion) {
        this.termRegion = termRegion;
    }

    public Integer getTermRegionId() {
        return termRegionId;
    }

    public void setTermRegionId(Integer termRegionId) {
        this.termRegionId = termRegionId;
    }
}
