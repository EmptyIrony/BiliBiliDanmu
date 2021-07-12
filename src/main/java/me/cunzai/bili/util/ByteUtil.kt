package me.cunzai.bili.util

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/11/22 21:34
 * 4
 */
object ByteUtil {
    /**
     * @param hexStr Hex 字符串
     * @return byte[]
     */
    fun hexToByteArray(input: String): ByteArray {
        var hexStr = input
        if (hexStr.length % 2 == 1) {
            hexStr = "0$hexStr"
        }
        val hexlen = hexStr.length
        val result = ByteArray(hexlen / 2)
        var i = 0
        var j = 0
        while (i < hexlen) {
            result[j] = hexStr.substring(i, i + 2).toInt(16).toByte()
            i += 2
            j++
        }
        return result
    }

    /**
     * @param byteL left
     * @param byteR right
     * @return left + right
     */
    fun byteMerger(byteL: ByteArray, byteR: ByteArray): ByteArray {
        val byteArr = ByteArray(byteL.size + byteR.size)
        System.arraycopy(byteL, 0, byteArr, 0, byteL.size)
        System.arraycopy(byteR, 0, byteArr, byteL.size, byteR.size)
        return byteArr
    }
}