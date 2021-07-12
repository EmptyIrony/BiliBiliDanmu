package me.cunzai.bili

import me.cunzai.bili.danmuku.DanMuKu
import me.cunzai.bili.util.CC
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender


/*
 * @ Created with IntelliJ IDEA
 * @ Author EmptyIrony
 * @ Date 2021/7/12
 * @ Time 22:01
 */
object Command: CommandBase(){
    override fun getCommandName(): String {
        return "dmk"
    }

    override fun getCommandUsage(sender: ICommandSender?): String? {
        return null
    }

    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        if (args.size == 0) {
            CC.sendMsg("&c请输入房间号！")
            return
        }
        val arg = args[0]
        if (arg.equals("stop", ignoreCase = true)) {
            if (DanmuMod.INSTANCE.danMuKu != null) {
                DanmuMod.INSTANCE.danMuKu!!
                    .stop()
                DanmuMod.INSTANCE.danMuKu = null
                CC.sendMsg("&c已终止！")
            } else {
                CC.sendMsg("&c您尚未连接任何房间！")
            }
            return
        }
        if (DanmuMod.INSTANCE.danMuKu != null) {
            CC.sendMsg("&c请先输入/dmk stop 断开连接")
            return
        }
        val live: Int
        live = try {
            arg.toInt()
        } catch (ignore: NumberFormatException) {
            CC.sendMsg("&c请输入正确的房间号！")
            return
        }
        DanmuMod.INSTANCE.danMuKu = DanMuKu(live)
        DanmuMod.INSTANCE.danMuKu!!.init()
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}