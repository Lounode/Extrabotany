package io.github.lounode.extrabotany.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vazkii.botania.api.ServiceUtil;

public interface ExtraBotaniaAPI {
    String MODID = "extrabotania";
    Logger LOGGER = LoggerFactory.getLogger(MODID);

    ExtraBotaniaAPI INSTANCE = ServiceUtil.findService(ExtraBotaniaAPI.class, () -> new ExtraBotaniaAPI() {});

    static ExtraBotaniaAPI instance() {
        return INSTANCE;
    }
}
