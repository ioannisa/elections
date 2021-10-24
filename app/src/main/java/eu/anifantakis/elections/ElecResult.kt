package eu.anifantakis.elections

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * DOMAIN LEVEL Asteroid data class
 */
@Parcelize
data class ElectionResult (
    val idparty: Long,
    val party_title: String,
    val votes: Int,
    val party_perc: Double,
    val party_perc_str: String,
    val image_url: String
): Parcelable

/**
 * RETROFIT LEVEL Asteroid data class
 */

data class NetworkElecResultContainer(val elecResults: ArrayList<NetworkElecResult>)
data class NetworkElecResult (
    val locid: String,
    val locname: String,
    val ordering: String,
    val votes: String,
    val rank_w1: String,
    val party_perc: String,
    val idparty: String,
    val party_title: String,
    val total_tm: String,
    val included_tm: String,
    val ens: String,
    val registered: String,
    val voted: String,
    val invalid_votes: String,
    val blank_votes: String,
    val valid_votes: String
)

/**
 * ROOM LEVEL Asteroid data class (the Room Entity - table)
 */
@Entity
data class DatabaseElecResult (
    @PrimaryKey val idparty: Long,
    val party_title: String,
    val votes: Int,
    val party_perc: Double,
)

@Entity
data class PartyNote(
    @PrimaryKey val idparty: Long,
    val note: String
)

/**
 * Database Object -> Domain Object
 */
fun List<DatabaseElecResult>.asDomainModel(): List<ElectionResult> {
    return map {
        ElectionResult(
            idparty = it.idparty,
            party_title = it.party_title,
            votes = it.votes,
            party_perc = it.party_perc,
            party_perc_str = String.format("%.2f%%", it.party_perc),
            image_url =  Constants.BASE_URL+Constants.IMAGES_PATH+Constants.ELEC_ID+"/"+it.idparty+".png"
        )
    }
}

/**
 * Network Object -> Database Object
 */
fun NetworkElecResultContainer.asDatabaseModel(): Array<DatabaseElecResult> {
    return elecResults.map {
        DatabaseElecResult(
            idparty = it.idparty.toLong(),
            party_title = it.party_title,
            votes = it.votes.toInt(),
            party_perc = it.party_perc.toDouble()
        )
    }.toTypedArray()
}