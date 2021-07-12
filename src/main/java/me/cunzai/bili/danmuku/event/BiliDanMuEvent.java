package me.cunzai.bili.danmuku.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 22:17
 * 4
 */
public class BiliDanMuEvent extends Event {
    private final String cmd;
    private final String card;
    private final int cardLevel;
    private final String name;
    private final String msg;

    public BiliDanMuEvent(String cmd, String card, int cardLevel, String name, String msg) {
        this.cmd = cmd;
        this.card = card;
        this.cardLevel = cardLevel;
        this.name = name;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }
}
