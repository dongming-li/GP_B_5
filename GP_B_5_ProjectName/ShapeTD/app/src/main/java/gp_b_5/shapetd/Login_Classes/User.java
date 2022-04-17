package gp_b_5.shapetd.Login_Classes;

/**
 * Created by Joseph Nunez on 9/24/2017.
 */

public class User
{
    private String  userName;
    private String     level;
    private String     experience;
    private String     kills;
    private String accountType;
    public User(String username, String level, String experience, String kills, String accountType)
    {
        this.userName   = username;
        this.level = level;
        this.experience = experience;
        this.kills = kills;
        this.accountType = accountType;
    }

    public String getUserName()
    {
        return userName;
    }
    public String getLevel()
    {
        return level;
    }
    public String getExperience()
    {
        return experience;
    }
    public String getKills()
    {
        return kills;
    }
    public String getAccountType() { return accountType; }
}
