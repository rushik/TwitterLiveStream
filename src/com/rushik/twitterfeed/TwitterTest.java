package com.rushik.twitterfeed;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.List;

public class TwitterTest 
{
	public static void main(String[] args) throws TwitterException, IOException 
	{
		// Configuration...
		
		// please note below credentials for @rushik_life
		ConfigurationBuilder confbuilder  = new ConfigurationBuilder();
	    String token = "XYZ";// load from a persistent store
	    String tokenSecret = "XYZ"; // load from a persistent store
	    AccessToken accesstoken = new AccessToken(token, tokenSecret);
		confbuilder.setOAuthAccessToken(accesstoken.getToken());
		confbuilder.setOAuthAccessTokenSecret(accesstoken.getTokenSecret());
		confbuilder.setOAuthConsumerKey("XYZ"); 
		confbuilder.setOAuthConsumerSecret("XYZ");
		
		// API (single event)
		//Twitter twitter = new TwitterFactory(confbuilder.build()).getInstance();
	    //List<Status> statuses = twitter.getHomeTimeline();
	    //System.out.println("Showing My timeline.");
	    //for (Status status : statuses) 
	    //{
	    //    System.out.println(status.getUser().getName() + ":" + status.getText());  
	    //}	
	    
	    // update status
	    //Status status = twitter.updateStatus("Tweet Testing...");
	    
	    // implement status listner class 
	    StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) 
	        {
	            //System.out.println(status.getUser().getName() + " : " + status.getText());
	            
	            User user = status.getUser();
                String userContainer = status.getUser().toString(); // full structure of tweet data (see Status class)
                String displayName = status.getUser().getName();
                String userID = status.getUser().getScreenName();
                String profileLocation = user.getLocation();
                //String geoLocation = status.getGeoLocation().toString();
                long tweetId = status.getId(); 
                String content = status.getText();
                //System.out.println(usernameId + " : " + username + " : " + profileLocation + " : " + tweetId + " : " + content);
                System.out.println(displayName + " : " + profileLocation + " : " + content);
	            
	        }
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) 
	        {
	        	System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	        }
	        public void onException(Exception ex)
	        {
	            ex.printStackTrace();
	        }
			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
			{	
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}
			@Override
			public void onScrubGeo(long userId, long upToStatusId)
			{
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}
			@Override
			public void onStallWarning(StallWarning warning)
			{
				System.out.println("Got stall warning:" + warning);
			}
	    };
	    
	    // Stream Events
	    
	    // keyword based
	    String trackWords[] = {"paypal", "ebay"};
	    FilterQuery fQuery = new FilterQuery ();
	    fQuery.track(trackWords);
	    
	    // location based (The first pair must be the SW corner of the box - format EW, NS) 
	    // San Francisco, New York
	    //double locations[][] = {{-122.75, 36.8}, {-121.75, 37.8}};
	    // gujarat
	    //double locations[][] = { {68.00, 20.00}, {75.00, 25.00} };
	    // mumbai
	    //double locations[][] = { {72.00, 17.00}, {73.00, 20.00} };
	    // delhi and UP
	    //double locations[][] = { {75.00, 25.00}, {80.00, 30.00} };
	    // india 
	    //double locations[][] = { {65.00, 7.50}, {100.00, 38.00} };
	    //FilterQuery fQuery = new FilterQuery ();
	    //fQuery.locations(locations);
	    
	    TwitterStream twitterStream = new TwitterStreamFactory(confbuilder.build()).getInstance();
	    twitterStream.addListener(listener);
	    twitterStream.filter(fQuery);
	    
	    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	    //twitterStream.sample();
	}
}
