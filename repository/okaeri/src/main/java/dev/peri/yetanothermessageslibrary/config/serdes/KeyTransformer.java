package dev.peri.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import net.kyori.adventure.key.Key;

public class KeyTransformer extends BidirectionalTransformer<String, Key> {

    @Override
    public GenericsPair<String, Key> getPair() {
        return this.genericsPair(String.class, Key.class);
    }

    @Override
    public Key leftToRight(String data, SerdesContext serdesContext) {
        return Key.key(data);
    }

    @Override
    public String rightToLeft(Key data, SerdesContext serdesContext) {
        return data.asString();
    }

}
