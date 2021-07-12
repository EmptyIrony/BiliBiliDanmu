package me.cunzai.bili.util

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import java.util.regex.Pattern


class CC {
    companion object {
        @Transient
        private val SECTOR_SYMBOL = "§"

        @Transient
        private val ALL_PATTERN = "[0-9A-FK-ORa-fk-or]"

        @Transient
        private val REPLACE_PATTERN = Pattern.compile("&&(?=" + ALL_PATTERN + ")")

        @Transient
        private val VANILLA_PATTERN = Pattern.compile(SECTOR_SYMBOL + "+(" + ALL_PATTERN + ")")
        private val LETTERS_NUMBERS = Pattern.compile("[^a-z A-Z:0-9/']")
        private val KEEP_NUMBERS = Pattern.compile("[^0-9]")
        private val SCOREBOARD_CHARACTERS = Pattern.compile("[^a-z A-Z:0-9/'.!§\\[\\]❤]")
        fun translate(input: String): String {
            return input.replace("&", "§")
        }

        fun stripColor(input: String?): String {
            return VANILLA_PATTERN.matcher(input).replaceAll("")
        }

        fun keepLettersAndNumbersOnly(text: String?): String {
            return LETTERS_NUMBERS.matcher(text).replaceAll("")
        }

        fun keepNumbersOnly(input: String?): String {
            return KEEP_NUMBERS.matcher(input).replaceAll("").trim { it <= ' ' }
        }

        fun sendDanMuToPlayer(msg: String) {
            if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().thePlayer == null) {
                return
            }
            val thePlayer = Minecraft.getMinecraft().thePlayer
            thePlayer.addChatComponentMessage(ChatComponentText(translate("&b[BiliBili]&f$msg")))
        }

        fun keepScoreboardCharacters(text: String?): String {
            return SCOREBOARD_CHARACTERS.matcher(text).replaceAll("")
        }

        fun sendMsg(msg: String) {
            Minecraft.getMinecraft()?.ingameGUI?.chatGUI?.printChatMessage(ChatComponentText(translate(msg)))
        }

    }
}
