package me.cunzai.bili.danmuku.client;


import me.cunzai.bili.danmuku.handle.MessageHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 22:02
 * 4
 */
public class WebSocket extends WebSocketClient {
    private final MessageHandler messageHandler;

    public WebSocket(URI serverUri) {
        super(serverUri);
        this.messageHandler = new MessageHandler();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    public void onMessage(ByteBuffer bytes) {
        try {
            this.messageHandler.messageHandle(bytes);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
