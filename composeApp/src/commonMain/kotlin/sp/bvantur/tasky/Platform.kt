package sp.bvantur.tasky

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
