package com.github.l3nnartt.serverspoofer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class ServerFetcher implements Runnable {

    private final Consumer<String[]> callback;

    public ServerFetcher(Consumer<String[]> callback) {
        this.callback = callback;
    }

    public JsonElement getURLContent(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setConnectTimeout(5000);
        con.connect();
        String jsonString = IOUtils.toString(con.getInputStream(), "UTF-8");
        JsonParser parser = new JsonParser();
        return  parser.parse(jsonString);
    }

    public void fetch() {
        try {
            JsonArray config = getURLContent("http://dl.lennartloesche.de/bugfixes/servers.json").getAsJsonArray();
            String[] servers = new String[config.size()];
            for (int i = 0; i < config.size(); i++) {
                servers[i] = config.get(i).getAsString();
            }
            callback.accept(servers);
        } catch (IOException e) {
            e.printStackTrace();
            callback.accept(new String[]{
                    "gommehd.net",
                    "hypixel.net"
            });
        }
    }

    @Override
    public void run() {
        fetch();
    }
}