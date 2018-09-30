package moon.net.datamonitor.model

import java.util.*

class Photo(val id : Int, val url : String, val description: String,
            val dateAdded: Date, val isMain : Boolean, val user: User, val userId : Int) {}