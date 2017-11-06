import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebPage {
    String url;
    Document content;

    public WebPage(String url) throws IOException {
        this.url = url;
        this.content = getWebpageContent();

    }

    Document getWebpageContent() throws IOException {
        return Jsoup.connect(this.url).timeout(20*1000).get();
    }

    List<WebPage> all_pages() throws IOException {
        try {
            Elements links = this.content.getElementsByClass("paginator-catalog-l-link");
            int page_number = Integer.parseInt(links.last().text());

            List<WebPage> pages = new ArrayList<WebPage>();

            for (int i = 1; i <= page_number; i++) {
                pages.add(new WebPage(this.url + "page=" + i + "/"));
            }

            return pages;
        }catch (Exception exp){
            return new ArrayList<WebPage>();
        }
    }

    List<WebPage> items_comments_pages() throws IOException {
        try {
            Elements titles = this.content.getElementsByClass("g-i-tile-i-title").select("a");
            List<WebPage> comments_urls = new ArrayList<WebPage>();
            for (Element title : titles) {
                comments_urls.add(new WebPage(title.attr("href") + "comments/"));
            }
            return comments_urls;
        }catch (Exception exp){
            return new ArrayList<WebPage>();
        }
    }

    List<String> parse_comments() {

            Elements items = this.content.select("article");
            //items.select("");
            //System.out.println(items.size());
            List<String> comments = new ArrayList<String>();

            for (Element el : items) {
                try {
                String mark = el.getElementsByClass("sprite g-rating-stars-i").attr("content");
                String comment = el.getElementsByClass("pp-review-text-i").first().text();
                if (mark.length() > 0) {
                    comments.add(mark + ',' + comment);
                    //System.out.println(mark+','+comment);
                }
                }catch (Exception exp){
                    int k=0;
                }
            }
            //System.out.println(comments.size());
            return comments;

    }
}
