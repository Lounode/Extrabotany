package io.github.lounode.extrabotany.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vazkii.botania.api.ServiceUtil;

public interface ExtraBotanyAPI {
    String MODID = "extrabotany";
    Logger LOGGER = LoggerFactory.getLogger(MODID);

    ExtraBotanyAPI INSTANCE = ServiceUtil.findService(ExtraBotanyAPI.class, () -> new ExtraBotanyAPI() {});

    static ExtraBotanyAPI instance() {
        return INSTANCE;
    }
}
