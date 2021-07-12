package me.cunzai.bili.danmuku.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 22:36
 * 4
 */

public class BiliDanMuGiftEvent extends Event {
    private final String cmd;
    private final String card;
    private final int cardLevel;
    private final String name;
    private final String gift;
    private final int num;

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

    public String getGift() {
        return gift;
    }

    public int getNum() {
        return num;
    }

    public BiliDanMuGiftEvent(String cmd, String card, int cardLevel, String name, String gift, int num) {
        this.cmd = cmd;
        this.card = card;
        this.cardLevel = cardLevel;
        this.name = name;
        this.gift = gift;
        this.num = num;
    }
}
