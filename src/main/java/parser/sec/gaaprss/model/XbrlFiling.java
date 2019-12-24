package parser.sec.gaaprss.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XbrlFiling {

    private String companyName;
    private String formType;
    private Date filingDate;
    private String cikNumber;
    private String accessionNumber;
    private String fileNumber;
    private String acceptanceDateTime;
    private String period;
    private String assistantDirector;
    private String assignedSic;
    private String fiscalYearEnd;
    private List<XbrlFile> xbrlFiles = new ArrayList<>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getCikNumber() {
        return cikNumber;
    }

    public void setCikNumber(String cikNumber) {
        this.cikNumber = cikNumber;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getAcceptanceDateTime() {
        return acceptanceDateTime;
    }

    public void setAcceptanceDateTime(String acceptanceDateTime) {
        this.acceptanceDateTime = acceptanceDateTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAssistantDirector() {
        return assistantDirector;
    }

    public void setAssistantDirector(String assistantDirector) {
        this.assistantDirector = assistantDirector;
    }

    public String getAssignedSic() {
        return assignedSic;
    }

    public void setAssignedSic(String assignedSic) {
        this.assignedSic = assignedSic;
    }

    public String getFiscalYearEnd() {
        return fiscalYearEnd;
    }

    public void setFiscalYearEnd(String fiscalYearEnd) {
        this.fiscalYearEnd = fiscalYearEnd;
    }

    public List<XbrlFile> getXbrlFiles() {
        return xbrlFiles;
    }

    public void setXbrlFiles(List<XbrlFile> xbrlFiles) {
        this.xbrlFiles = xbrlFiles;
    }
}
