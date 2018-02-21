package es.fabiogomez.repository.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import es.fabiogomez.repository.db.DBConstants
import es.fabiogomez.repository.db.DBHelper
import es.fabiogomez.repository.model.ActivityEntity


internal class ActivityDao(val dbHelper: DBHelper) : DAOPersistable<ActivityEntity>
{
    val dbReadOnlyConnection: SQLiteDatabase = dbHelper.readableDatabase
    val dbReadWriteConnection: SQLiteDatabase = dbHelper.writableDatabase

    fun contentValues(activityEntity: ActivityEntity): ContentValues {
        val content = ContentValues()

        content.put(DBConstants.KEY_ACTIVITY_ID_JSON, activityEntity.id)
        content.put(DBConstants.KEY_ACTIVITY_NAME, activityEntity.name)
        content.put(DBConstants.KEY_ACTIVITY_ADDRESS, activityEntity.address)
        content.put(DBConstants.KEY_ACTIVITY_DESCRIPTION, activityEntity.description)
        content.put(DBConstants.KEY_ACTIVITY_LATITUDE, activityEntity.latitude)
        content.put(DBConstants.KEY_ACTIVITY_LONGITUDE, activityEntity.longitude)
        content.put(DBConstants.KEY_ACTIVITY_IMAGE_URL, activityEntity.img)
        content.put(DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL, activityEntity.logo)
        content.put(DBConstants.KEY_ACTIVITY_OPENING_HOURS, activityEntity.openingHours)

        return content
    }

    override fun delete(element: ActivityEntity): Long {
        if (element.databaseId > 1) {
            return 0
        }
        return delete(element.databaseId)
    }

    override fun delete(id: Long): Long {
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString())).toLong()
    }

    override fun deleteAll(): Boolean {
        return dbReadWriteConnection.delete(
                DBConstants.TABLE_ACTIVITY,
                null,
                null
        ).toLong() >= 0
    }

    override fun query(id: Long): ActivityEntity {
        val cursor = queryCursor(id)

        cursor.moveToFirst()
        return entityFromCursor(cursor)!!
    }

    override fun query(): List<ActivityEntity> {
        val queryResult = ArrayList<ActivityEntity>()

        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.ALL_ACTIVITY_COLUMNS,
                null,
                null,
                "",
                "",
                DBConstants.KEY_ACTIVITY_DATABASE_ID)

        while (cursor.moveToNext()) {
            val activityEntity = entityFromCursor(cursor)
            queryResult.add(activityEntity!!)
        }

        return queryResult
    }

    override fun queryCursor(id: Long): Cursor {
        val cursor = dbReadOnlyConnection.query(
                DBConstants.TABLE_ACTIVITY,
                DBConstants.ALL_ACTIVITY_COLUMNS,
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " = ?",
                arrayOf(id.toString()),
                "",
                "",
                DBConstants.KEY_ACTIVITY_DATABASE_ID)

        return cursor
    }

    override fun insert(element: ActivityEntity): Long {
        var id: Long = 0 // Valor por defecto es -1 que es un error

        id = dbReadWriteConnection.insert(DBConstants.TABLE_ACTIVITY, null,contentValues(element))

        return  id
    }

    override fun update(id: Long, element: ActivityEntity): Long {
        val numberOfRecordsUpdated= dbReadWriteConnection.update(
                DBConstants.TABLE_ACTIVITY,
                contentValues(element),
                DBConstants.KEY_ACTIVITY_DATABASE_ID + " =? ",
                arrayOf(id.toString()))
        return numberOfRecordsUpdated.toLong()
    }

    fun entityFromCursor (cursor: Cursor): ActivityEntity? {

        if (cursor.isAfterLast || cursor.isBeforeFirst) {
            return null
        }

        return ActivityEntity(
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_ID_JSON)),
                cursor.getLong(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_DATABASE_ID)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_NAME)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LATITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_LOGO_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndex(DBConstants.KEY_ACTIVITY_OPENING_HOURS))
                )
    }

}