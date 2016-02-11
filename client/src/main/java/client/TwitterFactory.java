package client;

import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFactory {
	
	private static Twitter twitter = null;
    
   public static Twitter getInstance() {
	   if(twitter == null){
		   ConfigurationBuilder cb = new ConfigurationBuilder();
		   cb.setDebugEnabled(true)
		           .setOAuthConsumerKey("aa")
		           .setOAuthConsumerSecret("aa")
		           .setOAuthAccessToken("ss")
		           .setOAuthAccessTokenSecret("ss");
	
		   twitter = new twitter4j.TwitterFactory(cb.build()).getInstance();
	   }
	   return twitter;
   }
   


}
