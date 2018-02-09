package es.fabiogomez.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import es.fabiogomez.repository.db.DBConstants
import es.fabiogomez.repository.db.DBHelper
import es.fabiogomez.repository.model.ShopEntity


internal class ShopDao (val dbHelper: DBHelper) : DAOPersistable<ShopEntity>
{
    private val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    private val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase


    override fun insert(element: ShopEntity): Long {
        var id: Long = 0 // Valor por defecto es -1 que es un error

        dbReadWriteConnection.beginTransaction()
        try {
            id = dbReadWriteConnection.insert(DBConstants.TABLE_SHOP, null,contentValues(element))
        } finally {
            dbReadWriteConnection.endTransaction()
            dbReadWriteConnection.close()
        }

        return  id
    }

    fun contentValues(shopEntity: ShopEntity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_SHOP_ID_JSON, shopEntity.id)
        content.put(DBConstants.KEY_SHOP_NAME, shopEntity.name)
        content.put(DBConstants.KEY_SHOP_ADDRESS, shopEntity.address)
        content.put(DBConstants.KEY_SHOP_DESCRIPTION, shopEntity.description)
        content.put(DBConstants.KEY_SHOP_LATITUDE, shopEntity.latitude)
        content.put(DBConstants.KEY_SHOP_LONGITUDE, shopEntity.longitude)
        content.put(DBConstants.KEY_SHOP_IMAGE_URL, shopEntity.img)
        content.put(DBConstants.KEY_SHOP_LOGO_IMAGE_URL, shopEntity.logo)
        content.put(DBConstants.KEY_SHOP_OPENING_HOURS, shopEntity.openingHours)

        return content
    }

    override fun delete(element: ShopEntity): Long {
        if (element.databaseId > 1) {
            return 0
        }
        return delete(element.databaseId)
    }

    override fun delete(id: Long): Long {

        dbReadWriteConnection.beginTransaction()

        try {
            return dbReadWriteConnection.delete(
                    DBConstants.TABLE_SHOP,
                    DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                    arrayOf(id.toString())).toLong()
        } finally {
            dbReadWriteConnection.endTransaction()
            dbReadWriteConnection.close()
        }

    }

    override fun deleteAll(): Boolean {
        dbReadWriteConnection.beginTransaction()
        try {
            return dbReadWriteConnection.delete(
                    DBConstants.TABLE_SHOP,
                    null,
                    null
            ).toLong() >= 0
        } finally {
            dbReadWriteConnection.endTransaction()
            dbReadWriteConnection.close()
        }

    }

    override fun query(id: Long): ShopEntity {
        val cursor = queryCursor(id)

        cursor.moveToFirst()

        return entityFromCursor(cursor)!!

    }

    override fun query(): List<ShopEntity> {
        val queryResult = ArrayList<ShopEntity>()

        val cursor = dbReadOnlyConnection.query(
                                                DBConstants.TABLE_SHOP,
                                                DBConstants.ALL_COLUMNS,
                                                null,
                                                null,
                                                "",
                                                "",
                                                DBConstants.KEY_SHOP_DATABASE_ID)

        while (cursor.moveToNext()) {
            val shopEntity = entityFromCursor(cursor)
            queryResult.add(shopEntity!!)
        }
        dbReadOnlyConnection.close()
        return queryResult
    }

    override fun queryCursor(id: Long): Cursor {
        val cursor = dbReadOnlyConnection.query(
                                                DBConstants.TABLE_SHOP,
                                                DBConstants.ALL_COLUMNS,
                                        DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                                                arrayOf(id.toString()),
                                                            "",
                                                             "",
                                                DBConstants.KEY_SHOP_DATABASE_ID)
        dbReadOnlyConnection.close()
        return cursor
    }

    override fun update(id: Long, element: ShopEntity): Long {

        try {
            val numberOfRecordsUpdated= dbReadWriteConnection.update(
                    DBConstants.TABLE_SHOP,
                    contentValues(element),
                    DBConstants.KEY_SHOP_DATABASE_ID + " =? ",
                    arrayOf(id.toString()))

            return numberOfRecordsUpdated.toLong()
        } finally {
            dbReadWriteConnection.endTransaction()
            dbReadWriteConnection.close()
        }

    }

    fun entityFromCursor (cursor: Cursor): ShopEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return ShopEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS)))
    }

}