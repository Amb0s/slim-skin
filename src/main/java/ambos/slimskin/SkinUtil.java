package ambos.slimskin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkinUtil {
    private static final Map<String, String> UUIDS = new HashMap<>();
    private static final Map<String, Skin> saved = new HashMap<>();
    private static final Gson gson = new Gson();

    private static String getUUID(String name) {
        if (UUIDS.containsKey(name)) {
            return UUIDS.get(name);
        } else {
            String UUID;

            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);

                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
                Pattern pattern = Pattern.compile("([0-9a-f]{32})");

                String content = null;
                String line;

                while ((line = input.readLine()) != null && content == null) {
                    if (!line.isEmpty()) {
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            content = matcher.group(1);
                        }
                    }
                }

                input.close();
                UUID = content;
            } catch (IOException var7) {
                UUID = null;
            }

            if (UUID != null) {
                UUIDS.put(name, UUID);
            }

            return UUID;
        }
    }

    private static JsonObject readJson(String ur) {
        try {
            URL url = new URL(ur);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder lines = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                if (!line.isEmpty()) {
                    lines.append(line);
                }
            }

            in.close();

            return gson.fromJson(lines.toString(), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Skin getSkin(URL url) {
        JsonObject json = readJson(url.toString());

        if (json == null) {
            return new Skin(null, false);
        }

        if (json.get("properties") != null) {
            JsonObject properties = ((JsonArray) json.get("properties")).get(0).getAsJsonObject();

            String value = properties.get("value").getAsString();
            String toJson = new String(Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)));

            JsonObject user = gson.fromJson(toJson, JsonObject.class);
            JsonObject textures = user.get("textures").getAsJsonObject();

            if (textures == null || textures.get("SKIN") == null) {
                return new Skin(null, false);
            }

            JsonObject skin = textures.get("SKIN").getAsJsonObject();
            String skinUrl = skin.get("url").getAsString();

            if (skin.get("metadata") != null) {
                String model = skin.get("metadata").getAsJsonObject().get("model").getAsString();

                if (model.equalsIgnoreCase("slim")) {

                    return new Skin(skinUrl, true);
                }
            }

            return new Skin(skinUrl, false);
        }

        return new Skin(null, false);
    }

    public static class Skin {
        public final boolean isSlim;
        public final String url;

        Skin(String url, boolean isSlim) {
            this.isSlim = isSlim;
            this.url = url;
        }
    }

    public static Skin getSkin(String name) {
        if (name == null) {
            return null;
        }

        if (saved.containsKey(name)) {
            return saved.get(name);
        }

        String UUID = getUUID(name);

        if (UUID == null) {
            saved.put(name, new Skin(null, false));

            return null;
        }

        String s = "https://sessionserver.mojang.com/session/minecraft/profile/" + UUID + "?unsigned=true";
        URL url;

        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            return null;
        }

        Skin skin = getSkin(url);
        saved.put(name, skin);

        return skin;
    }

    public static Boolean isSlim(String name) {
        Skin skin = getSkin(name);

        if (skin == null) {
            return false;
        }

        return skin.isSlim;
    }
}
