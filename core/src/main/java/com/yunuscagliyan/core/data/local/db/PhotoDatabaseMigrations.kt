package com.yunuscagliyan.core.data.local.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yunuscagliyan.core.util.Constant.DBUtil.PHOTO_ENTITY_NAME

/**
 * Adds the two Pixabay full-access URLs to the cached favourites.
 *
 * Written out rather than left to the destructive fallback: existing users would
 * otherwise lose every saved favourite on update.
 */
val MIGRATION_2_3 = Migration(2, 3) { db: SupportSQLiteDatabase ->
    db.execSQL("ALTER TABLE $PHOTO_ENTITY_NAME ADD COLUMN fullHDURL TEXT")
    db.execSQL("ALTER TABLE $PHOTO_ENTITY_NAME ADD COLUMN imageURL TEXT")
}
