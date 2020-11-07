package com.imagefinder.storage.local.repository.image

import com.imagefinder.core.presentation.useRealm
import com.imagefinder.domain.common.mapper.local.toImagesItems
import com.imagefinder.domain.entity.ImageItem
import com.imagefinder.storage.local.data.ImageItemDto
import com.imagefinder.storage.local.repository.common.RealmLocalStorage
import io.reactivex.Observable

class ImageRepositoryImpl(
    private val realmStorage: RealmLocalStorage
) : ImageRepository {

    override fun getAll(): Observable<List<ImageItem>> {
        return Observable.create { emitter ->
            realmStorage.openRealm().useRealm { realm ->
                try {
                    val entries = realm.where(ImageItemDto::class.java)
                        .findAll()

                    emitter.onNext(entries.toImagesItems())
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
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
