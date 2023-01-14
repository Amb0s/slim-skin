package ambos.slimskin;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SlimSkin implements ModInitializer {
    public static final String MOD_ID = "slimskin";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Map<String, Boolean> areSlim = new HashMap<>();

    public static String name(String name) {
        return SlimSkin.MOD_ID + "." + name;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("SlimSkin initialized");
    }
}
