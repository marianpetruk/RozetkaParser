import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileWriter {
    static void write(String path, List<String> comments) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        for(String comment:comments){
            writer.println(comment);
        }
        writer.close();
    }
}
