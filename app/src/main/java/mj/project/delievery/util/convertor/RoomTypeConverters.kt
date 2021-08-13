package mj.project.delievery.util.convertor

import androidx.room.TypeConverter

object RoomTypeConverters {

    @TypeConverter
    @JvmStatic  // static 변수의 get/set 함수를 자동으로 만들라는 의미
    fun toString(pair: Pair<Int, Int>): String {
        return "${pair.first},${pair.second}"
    }

    @TypeConverter
    @JvmStatic
    fun toIntPair(str: String): Pair<Int, Int> {
        val splitedStr = str.split(",")
        return Pair(Integer.parseInt(splitedStr[0]), Integer.parseInt(splitedStr[1]))
    }

}
