package parser.sec.gaaprss.model;

public class XbrlFile {

    private Integer sequence;
    private String file;
    private String type;
    private Long size;
    private String description;
    private Boolean inlineXbrl;
    private String url;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInlineXbrl() {
        return inlineXbrl;
    }

    public void setInlineXbrl(Boolean inlineXbrl) {
        this.inlineXbrl = inlineXbrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
