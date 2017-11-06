import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void main(String[] args) throws IOException {
        new File("data").mkdirs();
        String url = "https://rozetka.com.ua/ua/mobile-phones/c80003/";
        WebPage main_page = new WebPage(url);
        for(WebPage page:main_page.all_pages()){
            for(WebPage comment_page:page.items_comments_pages()){
                String filename = "data/" + comment_page.url.split("/")[4] + ".csv";

                List<String> all_comments = new ArrayList<String>();

                for(WebPage single_comments_page:comment_page.all_pages()){
                    all_comments.addAll(single_comments_page.parse_comments());
                }

                FileWriter.write(filename, all_comments);
                System.out.println(Integer.toString(all_comments.size()) + " comments found on " + comment_page.url);

            }

        }
    }
}
