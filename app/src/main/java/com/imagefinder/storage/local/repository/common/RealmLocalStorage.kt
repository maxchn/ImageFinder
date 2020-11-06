package com.imagefinder.storage.local.repository.common

import com.imagefinder.BuildConfig
import io.reactivex.Single
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

    protected fun <T : RealmModel> cache(data: T) {
        cache(listOf(data))
    }

    protected fun <T : RealmModel> cache(data: List<T>) {
        openRealm().use { realm ->
            realm.executeTransaction { it.insertOrUpdate(data) }
        }
    }

    protected inline fun <reified T : RealmModel> getAllData(): List<T> {
        openRealm().use {
            val all = it.where(T::class.java).findAll()
            return it.copyFromRealm(all)
        }
    }

    protected inline fun <reified T : RealmModel> getAllDataSingle(): Single<List<T>> {
        return Single.fromCallable {
            val result = getAllData<T>()
            result
        }
    }

    protected inline fun <reified T : RealmModel> getAllDataSingleOrError(): Single<List<T>> {
        return getAllDataSingle<T>().flatMap {
            return@flatMap if (it.isEmpty()) {
                Single.error(NoSuchElementException())
            } else {
                Single.just(it)
            }
        }
    }

    fun close(realm: Realm) {
        if (realm.isClosed.not()) {
            realm.close()
        }
    }

    companion object {
        const val ID_FIELD_NAME = "id"

        fun isNewEntry(id: Long): Boolean {
            return id <= 0
        }
    }
}
