package com.gokhandroid.demoecommerce.di.modules

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gokhandroid.demoecommerce.App
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.data.AppDatabase
import com.gokhandroid.demoecommerce.di.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @SuppressLint("NewApi")
    @Singleton
    @Provides
    fun provideContext(application: App): Context = application

    @Provides
    @DatabaseInfo
    fun providerDatabaseName(): String {
        return ConsRoomDB.DB_NAME
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @DatabaseInfo dbName: String,
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, dbName
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL("INSERT INTO PRODUCT VALUES(1,'Kalem',9.58);")
                    db.execSQL("INSERT INTO PRODUCT VALUES(2,'Kagit',0.60);")
                    db.execSQL("INSERT INTO PRODUCT VALUES(3,'Silgi',30.0);")
                    db.execSQL("INSERT INTO PRODUCT VALUES(4,'Defter',40.0);")
                    db.execSQL("INSERT INTO PRODUCT VALUES(5,'Kitap',50.82);")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {

                }
            })
            .allowMainThreadQueries()
            .build()
    }

}