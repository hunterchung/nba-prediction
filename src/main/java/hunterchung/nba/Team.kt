package hunterchung.nba

enum class Team(val id: String, val teamName: String) {
    ATLANTA_HAWKS("1610612737", "Hawks"),
    BOSTON_CELTICS("1610612738", "Celtics"),
    BROOKLYN_NETS("1610612751", "Nets"),
    CHARLOTTE_HORNETS("1610612766", "Hornets"),
    CHICAGO_BULLS("1610612741", "Bulls"),
    CLEVELAND_CAVALIERS("1610612739", "Cavaliers"),
    DALLAS_MAVERICKS("1610612742", "Mavericks"),
    DENVER_NUGGETS("1610612743", "Nuggets"),
    DETROIT_PISTONS("1610612765", "Pistons"),
    GOLDEN_STATE_WARRIORS("1610612744", "Warriors"),
    HOUSTON_ROCKETS("1610612745", "Rockets"),
    INDIANA_PACERS("1610612754", "Pacers"),
    LA_CLIPPERS("1610612746", "Clippers"),
    LOS_ANGELES_LAKERS("1610612747", "Lakers"),
    MEMPHIS_GRIZZLIES("1610612763", "Grizzlies"),
    MIAMI_HEAT("1610612748", "Heat"),
    MILWAUKEE_BUCKS("1610612749", "Bucks"),
    MINNESOTA_TIMBERWOLVES("1610612750", "Timberwolves"),
    NEW_ORLEANS_PELICANS("1610612740", "Pelicans"),
    NEW_YORK_KNICKS("1610612752", "Knicks"),
    OKLAHOMA_CITY_THUNDER("1610612760", "Thunder"),
    ORLANDO_MAGIC("1610612753", "Magic"),
    PHILADELPHIA_76ERS("1610612755", "76ers"),
    PHOENIX_SUNS("1610612756", "Suns"),
    PORTLAND_TRAIL_BLAZERS("1610612757", "Trail Blazers"),
    SACRAMENTO_KINGS("1610612758", "Kings"),
    SAN_ANTONIO_SPURS("1610612759", "Spurs"),
    TORONTO_RAPTORS("1610612761", "Raptors"),
    UTAH_JAZZ("1610612762", "Jazz"),
    WASHINGTON_WIZARDS("1610612764", "Wizards"),

    PLACEHOLDER("000", "NA");

    val readName = name.split("_").joinToString(" ") { it.toLowerCase().capitalize() }
}
