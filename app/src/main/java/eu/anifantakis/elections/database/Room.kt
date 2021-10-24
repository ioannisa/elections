package eu.anifantakis.elections.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import eu.anifantakis.elections.DatabaseElecResult
import eu.anifantakis.elections.PartyNote
import java.util.*

@Dao
interface ElectionsDao{
    // Get the party results
    @Query("select * from DatabaseElecResult order by party_perc desc")
    fun getElecResults(): LiveData<List<DatabaseElecResult>>

    // update party results
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllResults(vararg elecResult: DatabaseElecResult)

    // get a party's note
    @Query("select note from PartyNote where idparty=:ID_PARTY")
    fun getPartyNote(ID_PARTY: Long): String?

    // update a party's note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: PartyNote)

    @Query("delete from PartyNote where idparty=:ID_PARTY")
    fun removeNote(ID_PARTY: Long)
}

@Database(entities = [DatabaseElecResult::class, PartyNote::class], version = 1)
//@TypeConverters(Converters::class)
abstract  class ElectionsDatabase: RoomDatabase(){
    abstract val electionsDao: ElectionsDao
}

@Volatile
private lateinit var INSTANCE: ElectionsDatabase

fun getDatabase(context: Context): ElectionsDatabase{
    synchronized(ElectionsDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ElectionsDatabase::class.java,
                "elections").build()
        }
    }
    return INSTANCE
}
