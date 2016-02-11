package client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SimpleMqttClient implements MqttCallback {

  MqttClient myClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://ec2-54-208-228-243.compute-1.amazonaws.com:1883";
	static final String M2MIO_DOMAIN = "<Insert m2m.io domain here>";
	static final String M2MIO_STUFF = "things";
	static final String M2MIO_THING = "aa3";
	static final String M2MIO_USERNAME = "<m2m.io username>";
	static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";

	// the following two flags control whether this example is a publisher, a subscriber or both
	static final Boolean subscriber = false;
	static final Boolean publisher = true;


	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {
		Twitter twitter = TwitterFactory.getInstance();
		try {
			 ResponseList<Location> locations=twitter.getAvailableTrends();
			 System.out.println(locations);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
//		SimpleMqttClient smc = new SimpleMqttClient();
//		smc.runClient();
	}
	
	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient() {
		// setup MQTT Client
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
//		connOpt.setUserName(M2MIO_USERNAME);
//		connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
		
		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Connected to " + BROKER_URL);

		// setup topic
		// topics on m2m.io are in the form <domain>/<stuff>/<thing>
		String myTopic = "aa3";
		MqttTopic topic = myClient.getTopic(myTopic);


		// publish messages if publisher
		if (publisher) {
			for (int i=1; i<=10; i++) {
		   		String pubMsg = "{\"pubmsg\":" + i + "}";
		   		int pubQoS = 0;
				MqttMessage message = new MqttMessage(pubMsg.getBytes());
		    	message.setQos(pubQoS);
		    	message.setRetained(false);

		    	// Publish the message
		    	System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
		    	MqttDeliveryToken token = null;
		    	try {
		    		// publish message to broker
					token = topic.publish(message);
			    	// Wait until the message has been delivered to the broker
					token.waitForCompletion();
					Thread.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}			
		}
		try {
			myClient.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("Delivered");
		
	}

	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
}