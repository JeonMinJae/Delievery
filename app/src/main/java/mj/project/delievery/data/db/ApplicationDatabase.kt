package mj.project.delievery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import mj.project.delievery.data.db.dao.LocationDao
import mj.project.delievery.data.db.dao.RestaurantDao
import mj.project.delievery.data.entity.locaion.LocationLatLngEntity
import mj.project.delievery.data.entity.restaurant.RestaurantEntity

// 버전이 1 이기 때문에 앱 설치후 다시 실행시 에러가 뜰경우 앱 삭제후 다시 실행하면된다.
@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao(): RestaurantDao

}
