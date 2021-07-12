package me.cunzai.bili.danmuku.handle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.cunzai.bili.danmuku.event.BiliDanMuEvent;
import me.cunzai.bili.danmuku.event.BiliDanMuGiftEvent;
import me.cunzai.bili.danmuku.event.BiliDanMuJoinEvent;
import net.minecraftforge.common.MinecraftForge;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 22:14
 * 4
 */
public class MessageHandler {
    private static JsonParser parser = new JsonParser();

    /**
     * @param str 包含多条 message 的字符串
     * @return List<message>
     */
    private static List<String> splitStringToJson(String str) {
        List<String> result = new ArrayList<>();
        for (int i = 1, count = 1; i < str.length(); i++) {

            if (str.charAt(i) == '{') {
                count++;
            } else if (str.charAt(i) == '}') {
                count--;
            }

            if (count == 0) {
                result.add(str.substring(0, i + 1));
                int nextIndex = str.indexOf("{", i + 1);
                if (nextIndex != -1) {
                    result.addAll(splitStringToJson(str.substring(nextIndex)));
                }
                return result;
            }
        }
        return result;
    }

    public void handleByteBuffer(ByteBuffer buffer) throws DataFormatException {
        messageHandle(buffer);
    }

    public void messageHandle(ByteBuffer message) throws DataFormatException {
        List<String> s = messageToJson(message);
        for (String s1 : s) {
            JsonObject json = parser
                    .parse(s1)
                    .getAsJsonObject();

            String cmd = json.get("cmd")
                    .getAsString();

            if ("DANMU_MSG".equals(cmd)) {
                try {
                    JsonArray array = json.get("info")
                            .getAsJsonArray();
                    String msg = array.get(1).getAsString();
                    String sender = array.get(2).getAsJsonArray().get(1).getAsString();
                    JsonArray card = array.get(3).getAsJsonArray();
                    int level = 0;
                    String cardName = "";
                    if (card.size() > 0) {
                        level = card.get(0).getAsInt();
                        cardName = card.get(1).getAsString();
                    }

                    MinecraftForge
                            .EVENT_BUS
                            .post(new BiliDanMuEvent(cmd, cardName, level, sender, msg));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ("SEND_GIFT".equals(cmd)) {
                try {
                    JsonObject giftData = json.get("data")
                            .getAsJsonObject();
                    String sender = giftData.get("uname")
                            .getAsString();
                    String giftName = giftData.get("giftName")
                            .getAsString();
                    int num = giftData.get("num")
                            .getAsInt();

                    JsonElement medalInfo = giftData.get("medal_info");
                    String card = "";
                    int cardLevel = 0;
                    if (medalInfo != null && !medalInfo.isJsonNull()) {
                        card = medalInfo.getAsJsonObject()
                                .get("medal_name")
                                .getAsString();

                        cardLevel = medalInfo.getAsJsonObject()
                                .get("medal_level")
                                .getAsInt();
                    }
                    MinecraftForge.EVENT_BUS
                            .post(new BiliDanMuGiftEvent(cmd, card, cardLevel, sender, giftName, num));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if ("INTERACT_WORD".equals(cmd)) {
                JsonObject joinData = json.get("data")
                        .getAsJsonObject();
                String name = joinData.get("uname")
                        .getAsString();
                JsonObject medal = joinData.get("fans_medal")
                        .getAsJsonObject();

                String card = "";
                int cardLevel = 0;
                if (medal != null && !medal.isJsonNull()) {
                    card = medal.get("medal_name")
                            .getAsString();
                    cardLevel = medal.get("medal_level")
                            .getAsInt();
                }

                MinecraftForge.EVENT_BUS
                        .post(new BiliDanMuJoinEvent(cmd, card, cardLevel, name));
            } else if ("ENTRY_EFFECT".equals(cmd)) {
                JsonObject effect = json.get("data")
                        .getAsJsonObject();
            }
        }
    }

    /**
     * @param message 如果是 message 是弹幕类型，则需要解压拆分
     * @return List<message>
     * @throws DataFormatException DataFormatException
     */

    //fixme java.lang.IllegalStateException: Not a JSON Object: "\u0000\u0000\u0000\u0001" print: [0, 0, 0, 1]
    private List<String> messageToJson(ByteBuffer message) throws DataFormatException {
        byte[] messageBytes = message.array();
        byte[] mainMessageBytes = Arrays
                .copyOfRange(messageBytes, 16, messageBytes.length);

        if (messageBytes[16] != 120) {
            return Collections.singletonList(new String(mainMessageBytes, StandardCharsets.UTF_8));
        }

        // 解压缩弹幕信息
        byte[] newByte = new byte[1024 * 5];
        Inflater inflater = new Inflater();
        inflater.setInput(mainMessageBytes);
        newByte = Arrays.copyOfRange(newByte, 16, inflater.inflate(newByte));
        return splitStringToJson(new String(newByte, StandardCharsets.UTF_8));
    }
}
