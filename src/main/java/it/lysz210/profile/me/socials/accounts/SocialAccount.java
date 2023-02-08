package it.lysz210.profile.me.socials.accounts;

import lombok.Data;

import java.net.URL;

@Data
public class SocialAccount {
    /**
     * Account name
     */
    private String name;
    /**
     * Account username
     */
    private String username;
    /**
     * Account icon
     */
    private String icon;
    /**
     * Account color
     */
    private String color;
    /**
     * Account url
     */
    private URL url;
}
