package moon.net.datamonitor.model

import java.util.*

class User(val id : Int, val username : String, val knownAs: String,
           val age : String, val gender : String, val created : Date, val lastActive : Date,
           val photoUrl : String, val city : String, val country : String, val interests : String,
           val introduction : String, val lookingFor : String, val photos : List<Photo>) {}