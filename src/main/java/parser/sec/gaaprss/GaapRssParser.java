package parser.sec.gaaprss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parser.sec.gaaprss.model.Enclosure;
import parser.sec.gaaprss.model.Feed;
import parser.sec.gaaprss.model.Item;
import parser.sec.gaaprss.model.XbrlFile;
import parser.sec.gaaprss.model.XbrlFiling;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * SAX parser for the Securities and Exchange Commissions GAAP Filings RSS feed.
 *
 * @see https://www.sec.gov/Archives/edgar/usgaap.rss.xml
 */
public class GaapRssParser extends DefaultHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GaapRssParser.class);
    private static final String RSS_URL = "https://www.sec.gov/Archives/edgar/usgaap.rss.xml";

    public static void main(String... args) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser = saxParserFactory.newSAXParser();

        GaapRssParser handler = new GaapRssParser();

        LOG.info("Parsing: {}", RSS_URL);
        parser.parse(RSS_URL, handler);

        Feed feed = handler.getParsedFeed();

        LOG.info("Parsing complete!");
    }

    private final Feed feed = new Feed();

    private boolean processingItem = false;
    private String temp;
    private Item tempItem;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            processingItem = true;
            tempItem = new Item();
        }

        if (processingItem) {
            if (qName.equalsIgnoreCase("enclosure")) {
                Enclosure enclosure = new Enclosure();
                enclosure.setUrl(attributes.getValue("url"));
                enclosure.setLength(Long.parseLong(attributes.getValue("length")));
                enclosure.setType(attributes.getValue("type"));

                tempItem.setEnclosure(enclosure);
            }

            if (qName.equalsIgnoreCase("edgar:xbrlFiling")) {
                tempItem.setXbrlFiling(new XbrlFiling());
            }

            if (qName.equalsIgnoreCase("edgar:xbrlFile")) {
                XbrlFile xbrlFile = new XbrlFile();
                xbrlFile.setSequence(Integer.parseInt(attributes.getValue("edgar:sequence")));
                xbrlFile.setFile(attributes.getValue("edgar:file"));
                xbrlFile.setType(attributes.getValue("edgar:type"));
                xbrlFile.setSize(Long.parseLong(attributes.getValue("edgar:size")));
                xbrlFile.setDescription(attributes.getValue("edgar:description"));
                xbrlFile.setUrl(attributes.getValue("edgar:url"));

                if (attributes.getValue("edgar:inlineXBRL") != null && !attributes.getValue("edgar:inlineXBRL").isEmpty()) {
                    xbrlFile.setInlineXbrl(Boolean.parseBoolean(attributes.getValue("edgar:inlineXBRL")));
                } else {
                    xbrlFile.setInlineXbrl(false);
                }

                tempItem.getXbrlFiling().getXbrlFiles().add(xbrlFile);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            processingItem = false;
            feed.getItems().add(tempItem);
        }

        if (!processingItem) {
            // Processing Feed
            if (qName.equalsIgnoreCase("title")) {
                feed.setTitle(temp);
            }

            if (qName.equalsIgnoreCase("link")) {
                feed.setLink(temp);
            }

            if (qName.equalsIgnoreCase("description")) {
                feed.setDescription(temp);
            }

            if (qName.equalsIgnoreCase("language")) {
                feed.setLanguage(temp);
            }

            if (qName.equalsIgnoreCase("pubDate")) {
                //Mon, 23 Dec 2019 00:00:00 EST
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

                try {
                    feed.setPublishDate(sdf.parse(temp));
                } catch (ParseException e) {
                    throw new SAXException("Unable to parse pubDate: " + temp, e);
                }
            }

            if (qName.equalsIgnoreCase("lastBuildDate")) {
                //Mon, 23 Dec 2019 00:00:00 EST
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

                try {
                    feed.setLastBuildDate(sdf.parse(temp));
                } catch (ParseException e) {
                    throw new SAXException("Unable to parse lastBuildDate: " + temp, e);
                }
            }

        } else {
            // Processing Item

            if (qName.equalsIgnoreCase("title")) {
                tempItem.setTitle(temp);
            }

            if (qName.equalsIgnoreCase("link")) {
                tempItem.setLink(temp);
            }

            if (qName.equalsIgnoreCase("guid")) {
                tempItem.setGuid(temp);
            }

            if (qName.equalsIgnoreCase("description")) {
                tempItem.setDescription(temp);
            }

            if (qName.equalsIgnoreCase("pubDate")) {
                //Mon, 23 Dec 2019 00:00:00 EST
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

                try {
                    tempItem.setPublishDate(sdf.parse(temp));
                } catch (ParseException e) {
                    throw new SAXException("Unable to parse item pubDate: " + temp, e);
                }
            }

            if (qName.equalsIgnoreCase("edgar:companyName")) {
                tempItem.getXbrlFiling().setCompanyName(temp);
            }

            if (qName.equalsIgnoreCase("edgar:formType")) {
                tempItem.getXbrlFiling().setFormType(temp);
            }

            if (qName.equalsIgnoreCase("edgar:filingDate")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                try {
                    tempItem.getXbrlFiling().setFilingDate(sdf.parse(temp));
                } catch (ParseException e) {
                    throw new SAXException("Unable to parse edgar:filingDate: " + tempItem, e);
                }
            }

            if (qName.equalsIgnoreCase("edgar:cikNumber")) {
                tempItem.getXbrlFiling().setCikNumber(temp);
            }

            if (qName.equalsIgnoreCase("edgar:accessionNumber")) {
                tempItem.getXbrlFiling().setAccessionNumber(temp);
            }

            if (qName.equalsIgnoreCase("edgar:fileNumber")) {
                tempItem.getXbrlFiling().setFileNumber(temp);
            }

            if (qName.equalsIgnoreCase("edgar:acceptanceDatetime")) {
                tempItem.getXbrlFiling().setAcceptanceDateTime(temp);
            }

            if (qName.equalsIgnoreCase("edgar:period")) {
                tempItem.getXbrlFiling().setPeriod(temp);
            }

            if (qName.equalsIgnoreCase("edgar:assistantDirector")) {
                tempItem.getXbrlFiling().setAssistantDirector(temp);
            }

            if (qName.equalsIgnoreCase("edgar:assignedSic")) {
                tempItem.getXbrlFiling().setAssignedSic(temp);
            }

            if (qName.equalsIgnoreCase("edgar:fiscalYearEnd")) {
                tempItem.getXbrlFiling().setFiscalYearEnd(temp);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.temp = new String(ch, start, length);
    }

    public Feed getParsedFeed() {
        return this.feed;
    }
}
