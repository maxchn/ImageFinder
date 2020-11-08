package com.imagefinder.storage.local.repository.common

import com.imagefinder.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import io.realm.exceptions.RealmMigrationNeededException

open class RealmLocalStorage {

    fun openRealm(): Realm {
        return try {
            Realm.getDefaultInstance()
        } catch (e: RealmMigrationNeededException) {
            Realm.deleteRealm(
                RealmConfiguration.Builder()
                    .name(BuildConfig.DB_NAME)
                    .build()
            )

            Realm.getDefaultInstance()
        }
    }

    inline fun <reified T : RealmModel> calculateNextId(): Long {
        openRealm().use {
            val currentIdNum: Number? = it.where(T::class.java)
                .max(ID_FIELD_NAME)

            return if (currentIdNum == null) {
                1L
            } else {
                currentIdNum.toInt() + 1L
            }
        }
    }

    companion object {
        const val ID_FIELD_NAME = "id"

        fun isNewEntry(id: Long): Boolean {
            return id <= 0
        }
    }
}
