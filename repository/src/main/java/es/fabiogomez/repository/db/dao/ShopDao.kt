package es.fabiogomez.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import es.fabiogomez.repository.db.DBConstants
import es.fabiogomez.repository.db.DBHelper
import es.fabiogomez.repository.model.ShopEntity


internal class ShopDao (val dbHelper: DBHelper) : DAOPersistable<ShopEntity>
{
    val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

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
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_SHOP,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString())).toLong()
    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_SHOP,
                null,
                null
        ).toLong() >= 0
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
                DBConstants.ALL_SHOP_COLUMNS,
                null,
                null,
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID)

        while (cursor.moveToNext()) {
            val shopEntity = entityFromCursor(cursor)
            queryResult.add(shopEntity!!)
        }

        return queryResult
    }

    override fun queryCursor(id: Long): Cursor {
        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_SHOP,
                DBConstants.ALL_SHOP_COLUMNS,
                DBConstants.KEY_SHOP_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                DBConstants.KEY_SHOP_DATABASE_ID)

        return cursor
    }

    override fun insert(element: ShopEntity): Long {
        var id: Long = 0 // Valor por defecto es -1 que es un error

        id = dbReadWriteConnection.insert(DBConstants.TABLE_SHOP, null,contentValues(element))

        return  id
    }

    override fun update(id: Long, element: ShopEntity): Long {
        val numberOfRecordsUpdated= dbReadWriteConnection.update(
                DBConstants.TABLE_SHOP,
                contentValues(element),
                DBConstants.KEY_SHOP_DATABASE_ID + " =? ",
                arrayOf(id.toString()))
        return numberOfRecordsUpdated.toLong()
    }

    fun entityFromCursor (cursor: Cursor): ShopEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return ShopEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_SHOP_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_SHOP_OPENING_HOURS))
                )
    }

}