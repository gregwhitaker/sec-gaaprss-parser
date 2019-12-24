package parser.sec.gaaprss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parser.sec.gaaprss.model.Feed;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

        LOG.info("Parsing: {}", RSS_URL);
        parser.parse(RSS_URL, new GaapRssParser());
    }

    private final Feed feed = new Feed();

    private String temp;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.temp = new String(ch, start, length);
    }
}
