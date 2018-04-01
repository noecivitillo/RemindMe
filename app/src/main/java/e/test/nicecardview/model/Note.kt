package e.test.nicecardview.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Noe on 21/2/2018.
 */
@Entity(tableName = "notes")
data class Note (@ColumnInfo (name = "color")var color : Int){

    @ColumnInfo (name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long= 0
    @ColumnInfo(name = "content") var content : String = ""
    @ColumnInfo (name = "important") var important: Boolean = false
    @ColumnInfo (name = "reminder") var reminder: String = ""
    @ColumnInfo (name = "ready")var ready: Boolean = false
    @ColumnInfo(name = "createdDate") var createdDate : Long = System.currentTimeMillis()
    @ColumnInfo(name = "inverseDate") var inverseDate : Long = Long.MAX_VALUE - createdDate

}
