import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void main(String[] args) throws IOException {
        new File("data").mkdirs();
        String url = "https://rozetka.com.ua/ua/mobile-phones/c80003/";
        WebPage main_page = new WebPage(url);
        for(WebPage page:main_page.all_pages()){
//            System.out.println(page.url);
            for(WebPage comment_page:page.items_comments_pages()){
                //new file for item
                String filename = "data/" + comment_page.url.split("/")[4] + ".csv";
                PrintWriter writer = new PrintWriter(filename, "UTF-8");

                List<String> all_comments = new ArrayList<String>();

                for(WebPage single_comments_page:comment_page.all_pages()){
                    //parse all coments and write them
                    //System.out.print("new comments page   ");
                    all_comments.addAll(single_comments_page.parse_comments());
                    //System.out.println(all_comments.size());
                }
                for(String comment:all_comments){
                    writer.println(comment);
                }
                writer.close();
                System.out.println(Integer.toString(all_comments.size()) + " comments found on " + comment_page.url);

            }
            break;
        }
    }
}
