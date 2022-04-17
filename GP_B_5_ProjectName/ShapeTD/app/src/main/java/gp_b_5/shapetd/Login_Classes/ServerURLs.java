package gp_b_5.shapetd.Login_Classes;

/**
 * Created by Joseph Nunez on 9/24/2017.
 */

public class ServerURLs
{
    private static final String     SERVER_URL      = "http://proj-309-gp-b-5.cs.iastate.edu/Api.php?apicall=";
    public static final  String     URL_Register    = SERVER_URL+ "register";
    public static final  String     URL_Login       = SERVER_URL + "login";

    private static final String     LEADERBOARD_URL             = "http://proj-309-gp-b-5.cs.iastate.edu/leaderboard.php?apicall=";
    public static final String      LEADERBOARD_KILL_A          = LEADERBOARD_URL + "kills_asc";
    public static final String      LEADERBOARD_KILL_D          = LEADERBOARD_URL + "kills_desc";
    public static final String      LEADERBOARD_LEVEL_A         = LEADERBOARD_URL + "level_asc";
    public static final String      LEADERBOARD_LEVEL_D         = LEADERBOARD_URL + "level_desc";
    public static final String      LEADERBOARD_EXPERIENCE_A    = LEADERBOARD_URL + "experience_asc";
    public static final String      LEADERBOARD_EXPERIENCE_D    = LEADERBOARD_URL + "experience_desc";

    private static final String     MANAGEUSERS_URL             = "http://proj-309-gp-b-5.cs.iastate.edu/manageUsers.php?apicall=";
    public static final String      MANAGEUSERS_INIT            = MANAGEUSERS_URL + "init";

    private static final String     SAVELOAD_URL                = "http://proj-309-gp-b-5.cs.iastate.edu/saveLoad.php?apicall=";
    public static final String      SAVELOAD_SAVE               = SAVELOAD_URL + "save";
}
