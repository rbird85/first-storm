package client;

import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFactory {
	
	private static Twitter twitter = null;
    
   public static Twitter getInstance() {
	   if(twitter == null){
		   ConfigurationBuilder cb = new ConfigurationBuilder();
		   cb.setDebugEnabled(true)
		           .setOAuthConsumerKey("IOlX1r0MVVjzEpH22PZajYNb5")
		           .setOAuthConsumerSecret("RizCM7yC54N5G6nmyd0oB54YLk20zIuyFtAnAVbCR31hn8pHQQ")
		           .setOAuthAccessToken("71831310-pKcJgglZeOB1JRA7x9RhdRiPDl8gsNBhFjMRHxPA3")
		           .setOAuthAccessTokenSecret("EweApyNOUkaMmPJp1k0AU2bVHDiM3MA1aqBIjDPKYiQKu");
	
		   twitter = new twitter4j.TwitterFactory(cb.build()).getInstance();
	   }
	   return twitter;
   }
   


}
