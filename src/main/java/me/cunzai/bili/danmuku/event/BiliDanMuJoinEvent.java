package me.cunzai.bili.danmuku.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 22:43
 * 4
 */
public class BiliDanMuJoinEvent extends Event {
    private final String cmd;
    private final String card;
    private final int cardLevel;
    private final String name;

    public BiliDanMuJoinEvent(String cmd, String card, int cardLevel, String name) {
        this.cmd = cmd;
        this.card = card;
        this.cardLevel = cardLevel;
        this.name = name;
    }

    public String getCmd() {
        return cmd;
    }

    public String getCard() {
        return card;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public String getName() {
        return name;
    }
}
