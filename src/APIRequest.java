import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
public class APIRequest {
    public static void requestImgBBServer(String base64ImageString) throws IOException {
        //https://api.imgbb.com/
        //IMGBB sitesine JSoup kütüphanesini kullanarak POST Request atıyoruz.
        Connection.Response response = Jsoup.connect("https://api.imgbb.com/1/upload")
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .method(Connection.Method.POST)
                .data("key", "6fbd55fb9c26e10b47c96d87d7677d33")
                .data("expiration", "600")
                .data("image", base64ImageString)
                .timeout(5000)
                .userAgent("IMGBB Request")
                .execute();
        System.out.println(response.body());

    }
}
