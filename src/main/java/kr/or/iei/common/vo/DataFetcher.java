package kr.or.iei.common.vo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.iei.aside.model.vo.Product;
import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.weather.model.vo.Weather;

public class DataFetcher {
	// 네이버 쇼핑, 뉴스 API 호출
	public static Map<String, Object> fetchNaverData() {
        String clientId = "o3gVuFJjxpwDv5Y0Xmbj";
        String clientSecret = "D4hHT4K4TO";
        String productText = null;
        String newsText = null;

        try {
            productText = URLEncoder.encode("그래픽카드", "UTF-8");
            newsText = URLEncoder.encode("헤드라인", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String proApiURL = "https://openapi.naver.com/v1/search/shop?query=" + productText;
        String NewsApiURL = "https://openapi.naver.com/v1/search/news?query=" + newsText;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String prResponseBody = get(proApiURL, requestHeaders);
        String nwResponseBody = get(NewsApiURL, requestHeaders);

        List<Product> productItems = parseProductItems(prResponseBody);
        List<NewsItem> newsItems = parseNewsItems(nwResponseBody);

        // 반환값을 Map으로 묶어 반환
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("productItems", productItems);
        resultMap.put("newsItems", newsItems);

        return resultMap;
    }

    // GET 요청 처리 (네이버 API와 같은 요청 처리)
    private static String get(String apiUrl, Map<String, String> headers) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

 // 네이버 숍 파싱
    private static List<Product> parseProductItems(String jsonResponse) {
        List<Product> Items = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray itemsArray = jsonObject.getJSONArray("items");

        int limit = Math.min(itemsArray.length(), 3); // 3개 만큼 출력

        for (int i = 0; i < limit; i++) { // i < limit 조건으로 변경
            JSONObject itemObject = itemsArray.getJSONObject(i);

            String shopTitle = itemObject.getString("title");
            String shopLink = itemObject.getString("link");
            String shopImg = itemObject.getString("image");
            int shopLowPrice = itemObject.getInt("lprice");
            String shopName = itemObject.getString("mallName");
            String shopCategory1 = itemObject.getString("category1");

            Product productItem = new Product(shopTitle, shopLink, shopImg, shopLowPrice, shopName, shopCategory1);
            Items.add(productItem);
        }

        return Items;
    }

    // 네이버 뉴스 파싱
    private static List<NewsItem> parseNewsItems(String jsonResponse) {
        List<NewsItem> Items = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray itemsArray = jsonObject.getJSONArray("items");

        int limit = Math.min(itemsArray.length(), 5); // 5개 만큼 출력

        for (int i = 0; i < limit; i++) { // i < limit 조건으로 변경
            JSONObject itemObject = itemsArray.getJSONObject(i);

            String title = itemObject.getString("title");
            String link = itemObject.getString("link");
            String description = itemObject.getString("description");

            NewsItem newsItem = new NewsItem(title, link, description);
            Items.add(newsItem);
        }

        return Items;
    }
    
    // 날씨 API 호출
    public static Weather fetchWeatherData() {
        try {
            URL url = new URL("https://apihub.kma.go.kr/api/typ01/url/fct_afs_dl.php?reg=11B10101&disp=0&help=1&authKey=cJGQY1PQTnuRkGNT0H57zQ");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "CP949"));
            String inputLine;
            StringBuffer respon = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                respon.append(inputLine);
            }
            in.close();

            String responseStr = respon.toString();
            responseStr = responseStr.replace("#START7777#", "").replace("#7777END", "");

            List<Weather> weatherList = new ArrayList<>();
            Pattern pattern = Pattern.compile(
            	    "(\\w{8,9})\\s+" +              // 1. 코드 (11B10101)
            	    "(\\d{12})\\s+" +               // 2. 시작 시각 (202411211700)
            	    "(\\d{12})\\s+" +               // 3. 종료 시각 (202411211200)
            	    "(\\w{3})\\s+" +                // 4. 구분 코드 (A02)
            	    "(\\d)\\s+" +                   // 5. 구분 번호 (0, 1, ...)
            	    "(\\d{3})\\s+" +                // 6. 지역 코드 (109)
            	    "(\\d)\\s+" +                   // 7. 지역 세부 코드 (2)
            	    "(\\w+)\\s+" +                  // 8. 지역 이름 (imch)
            	    "([가-힣]+)\\s+" +              // 9. 사용자 이름 (임충환)
            	    "(\\w+)\\s+" +                  // 10. 방향 코드 (W, NW, ...)
            	    "(\\d)\\s+" +                   // 11. 방향 세부 코드 (1)
            	    "(\\w{1,2})\\s+" +              // 12. 이동 방향 (NW, N, ...)
            	    "(\\d{1,2})\\s+" +              // 13. 속도1 (숫자)
            	    "(\\d{1,2})\\s+" +              // 14. 속도2 (숫자)
            	    "(\\w{4})\\s+" +                // 15. DB 코드 (DB01, DB03, ...)
            	    "(\\d)\\s+" +                   // 16. 기타 코드 (0)
            	    "\"([^\"]*)\""                  // 17. 상태 메시지 ("맑음", "구름많음", ...)
            	);

            Matcher matcher = pattern.matcher(responseStr);

            while (matcher.find()) {
                String regId = matcher.group(1);
                String tmFc = matcher.group(2);
                String tmEf = matcher.group(3);
                String mod = matcher.group(4);
                String ne = matcher.group(5);
                String stn = matcher.group(6);
                String c = matcher.group(7);
                String manId = matcher.group(8);
                String manFc = matcher.group(9);
                String w1 = matcher.group(10);
                String t = matcher.group(11);
                String w2 = matcher.group(12);
                String ta = matcher.group(13);
                String st = matcher.group(14);
                String sky = matcher.group(15);
                String prep = matcher.group(16);
                String wf = matcher.group(17);

                Weather weather = new Weather(regId, tmFc, tmEf, mod, ne, stn, c, manId, manFc, w1, t, w2, ta, st, sky, prep, wf);
                weatherList.add(weather);
            }

            return weatherList.isEmpty() ? null : weatherList.get(0); // 첫 번째 날씨 데이터 반환

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
