package com.imagefinder.storage.local.repository.image

import com.imagefinder.core.presentation.useRealm
import com.imagefinder.core.presentation.useRealmWithObservable
import com.imagefinder.storage.local.data.ImageItemDto
import com.imagefinder.storage.local.repository.common.RealmLocalStorage
import io.reactivex.Observable

class ImageRepositoryImpl(
    private val realmStorage: RealmLocalStorage
) : ImageRepository {

    override fun getAll(): Observable<List<ImageItemDto>> {
        return Observable.create { emitter ->
            realmStorage.openRealm().useRealmWithObservable(emitter) {
                it.where(ImageItemDto::class.java)
                    .findAll()
            }
        }
    }

    override fun upsert(newItem: ImageItemDto) {
        realmStorage.openRealm().useRealm {
            it.beginTransaction()

            try {
                if (RealmLocalStorage.isNewEntry(newItem.id)) {
                    newItem.id = realmStorage.calculateNextId<ImageItemDto>()
                    it.insert(newItem)
                } else {
                    it.where(ImageItemDto::class.java)
                        .equalTo(ID_FIELD_NAME, newItem.id)
                        .findFirst()?.let { entry ->
                            entry.init(newItem)
                            it.insertOrUpdate(entry)
                        }
                }
                it.commitTransaction()
            } catch (e: Exception) {
                it.cancelTransaction()
            }
        }
    }

    override fun removeAll() {
        realmStorage.openRealm().useRealm {
            try {
                it.beginTransaction()
                it.where(ImageItemDto::class.java).findAll().deleteAllFromRealm()
                it.commitTransaction()
            } catch (e: Exception) {
                it.cancelTransaction()
            }
        }
    }

    companion object {
        private const val ID_FIELD_NAME = "id"
    }
}
