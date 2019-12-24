package parser.sec.gaaprss.model;

import java.util.Date;

public class Item {

    private String title;
    private String link;
    private String guid;
    private String description;
    private Date publishDate;
    private XbrlFiling xbrlFiling;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public XbrlFiling getXbrlFiling() {
        return xbrlFiling;
    }

    public void setXbrlFiling(XbrlFiling xbrlFiling) {
        this.xbrlFiling = xbrlFiling;
    }
}
