package me.cunzai.bili

import me.cunzai.bili.danmuku.DanMuKu
import me.cunzai.bili.danmuku.event.BiliDanMuEvent
import me.cunzai.bili.danmuku.event.BiliDanMuGiftEvent
import me.cunzai.bili.danmuku.event.BiliDanMuJoinEvent
import me.cunzai.bili.util.CC
import me.cunzai.bili.util.MedalColor
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


/*
 * @ Created with IntelliJ IDEA
 * @ Author EmptyIrony
 * @ Date 2021/7/12
 * @ Time 21:46
 */
@Mod(modid = "bilidanmu",name = "BiliBiliDanMu")
class DanmuMod {
    var danMuKu: DanMuKu? = null

    companion object{
        lateinit var INSTANCE: DanmuMod
    }

    @Mod.EventHandler
    fun onInstall(event: FMLInitializationEvent) {
        INSTANCE = this

        MinecraftForge.EVENT_BUS.register(this)
        ClientCommandHandler.instance
            .registerCommand(
                Command
            )
    }

    @SubscribeEvent
    fun onDanMu(event: BiliDanMuEvent) {
        val sb = StringBuilder()
        if (event.card != "") {
            sb.append(getColoredCard(event.cardLevel))
                .append("【")
                .append(event.card)
                .append(event.cardLevel)
                .append("】&3")
                .append(event.name)
                .append(": &7")
                .append(event.msg)
        } else {
            sb.append("&3")
                .append(event.name)
                .append(": &7")
                .append(event.msg)
        }
        CC.sendDanMuToPlayer(sb.toString())
    }

    @SubscribeEvent
    fun onGift(event: BiliDanMuGiftEvent) {
        val sb = StringBuilder()
        if (event.card != "") {
            sb.append(getColoredCard(event.cardLevel))
                .append("【")
                .append(event.card)
                .append(event.cardLevel)
                .append("】&6")
                .append(event.name)
                .append(" 赠送了 ")
                .append(event.gift)
                .append("*")
                .append(event.num)
        } else {
            sb.append("&6")
                .append(event.name)
                .append(" 赠送了 ")
                .append(event.gift)
                .append("*")
                .append(event.num)
        }
        CC.sendDanMuToPlayer(sb.toString())
    }

    @SubscribeEvent
    fun onJoin(event: BiliDanMuJoinEvent) {
        val sb = StringBuilder()
        if (event.card != "") {
            sb.append(getColoredCard(event.cardLevel))
                .append("【")
                .append(event.card)
                .append(event.cardLevel)
                .append("】&b")
                .append(event.name)
                .append(" 进入了直播间")
        } else {
            sb.append("&b")
                .append(event.name)
                .append(" 进入了直播间")
        }
        CC.sendDanMuToPlayer(sb.toString())
    }

    private fun getColoredCard(level: Int): String? {
            for (medalColor in MedalColor.values()) {
            if (medalColor.min <= level && medalColor.max >= level) {
                return medalColor.color
            }
        }
        return "&f"
    }

}