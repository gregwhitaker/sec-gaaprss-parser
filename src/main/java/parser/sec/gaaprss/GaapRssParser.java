package parser.sec.gaaprss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import parser.sec.gaaprss.model.Feed;

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

        Feed feed = handler.getFeed();
        boolean test = true;
    }

    private final Feed feed = new Feed();

    private String temp;
    private boolean processingItem = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            this.processingItem = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            this.processingItem = false;
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
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.temp = new String(ch, start, length);
    }

    public Feed getFeed() {
        return this.feed;
    }
}
