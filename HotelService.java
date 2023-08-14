package services;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotelService {

    private static final Logger logger = LoggerFactory.getLogger(HotelService.class);

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final String API_KEY = "";

    public static String getHotelsForCity(String cityName) throws IOException {
        String url = "" + cityName.replace(" ", "+") + "&key=" + API_KEY;

        Request request = new Request.Builder().url(url).get().build();

        try (Response response = CLIENT.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.info(" " + responseBody);
            return extractPlaces(responseBody);
        }
    }

    private static String extractPlaces(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray results = jsonObject.getJSONArray("results");
        StringBuilder places = new StringBuilder();

        for (int i = 0; i < Math.min(results.length(), 5); i++) {
            JSONObject result = results.getJSONObject(i);
            String name = result.getString("name");
            places.append("• ").append(name).append("\n");
        }
        return places.toString();
    }
}
