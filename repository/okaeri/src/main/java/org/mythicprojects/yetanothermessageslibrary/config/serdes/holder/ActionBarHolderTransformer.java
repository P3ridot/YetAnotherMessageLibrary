package org.mythicprojects.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import net.kyori.adventure.text.Component;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;

public class ActionBarHolderTransformer extends BidirectionalTransformer<Component, ActionBarHolder> {

    @Override
    public GenericsPair<Component, ActionBarHolder> getPair() {
        return this.genericsPair(Component.class, ActionBarHolder.class);
    }

    @Override
    public ActionBarHolder leftToRight(Component data, SerdesContext serdesContext) {
        return new ActionBarHolder(data);
    }

    @Override
    public Component rightToLeft(ActionBarHolder data, SerdesContext serdesContext) {
        return data.getMessage();
    }

}
