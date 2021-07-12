package me.cunzai.bili.danmuku;

import me.cunzai.bili.danmuku.client.WebSocket;
import me.cunzai.bili.util.ByteUtil;
import me.cunzai.bili.util.CC;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 21:25
 * 4
 */
public class DanMuKu {
    private static final String HEART_BEAT = "00000010001000010000000200000001";
    private static URI BILIBILI_URI;

    static {
        try {
            BILIBILI_URI = new URI("wss://broadcastlv.chat.bilibili.com:2245/sub");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private final int liveRoom;
    private final WebSocket client;
    private Timer heartBeatTimer;

    public DanMuKu(int liveRoom) throws URISyntaxException {
        this.liveRoom = liveRoom;
        this.client = new WebSocket(new URI("wss://broadcastlv.chat.bilibili.com:2245/sub"));
    }

    public void stop() {
        this.client.close();
        this.heartBeatTimer
                .cancel();
    }

    public void init() throws InterruptedException {
        CC.Companion.sendMsg("正在为您连接到房间: " + liveRoom);
        client.connectBlocking();

        byte[] headByte = ByteUtil.INSTANCE.hexToByteArray(this.getHead());
        byte[] bodyBytes = this.getBody().getBytes(StandardCharsets.UTF_8);
        byte[] requestCode = ByteUtil.INSTANCE.byteMerger(headByte, bodyBytes);

        client.send(requestCode);

        this.heartBeatTimer = new Timer();
        this.heartBeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (client.isClosed()){
                    cancel();
                    return;
                }
                client.send(ByteUtil.INSTANCE.hexToByteArray(HEART_BEAT));
            }
        }, 0, 30 * 1000);
        CC.Companion.sendMsg("连接成功！");

    }

    private String getHead() {
        return "000000{replace}001000010000000700000001".replace("{replace}", Integer
                .toHexString(this.getBody().getBytes().length + 16));
    }

    private String getBody() {
        return "{\"uid\":0,\"roomid\":" + liveRoom + ",\"protover\":1,\"platform\":\"web\",\"clientver\":\"1.5.10.1\",\"type\":2}";
    }
}
