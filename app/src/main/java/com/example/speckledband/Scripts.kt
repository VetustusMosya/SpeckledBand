package com.example.speckledband

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Scripts(
    val id: Int,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Scripts> {
        override fun createFromParcel(parcel: Parcel): Scripts {
            return Scripts(parcel)
        }

        override fun newArray(size: Int): Array<Scripts?> {
            return arrayOfNulls(size)
        }
    }
}
