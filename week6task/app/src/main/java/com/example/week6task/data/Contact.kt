package com.example.week6task.data

import com.google.firebase.database.Exclude

data class Contact(
    @get:Exclude //we do not want to resave the id to the firebase
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber:String? = null,

    @get:Exclude
    var isDeleted: Boolean = false

) { override fun equals(other: Any?):Boolean{
        return if(other is Contact){
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31*result+(firstName?.hashCode() ?: 0)
        result = 31*result+(lastName?.hashCode() ?: 0)
        result = 31*result+(phoneNumber?.hashCode() ?: 0)

        return result
    }
}