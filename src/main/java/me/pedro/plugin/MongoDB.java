package me.pedro.plugin;


import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bson.Document;
import org.bukkit.entity.Player;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import me.pedro.plugin.Player.PlayerData;

public class MongoDB {
	
	private Core plugin;
	
	HashMap<UUID, PlayerData> playerDataHashMap;
	public MongoCollection<Document> colletion;
	
	
	public MongoDB(Core plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("resource")
	public void mongoConnect() {
		
		String URI = "mongodb+srv://deathzone:12Q5byEXioenG24x@cluster0.nbbumhg.mongodb.net/?retryWrites=true&w=majority";
		
		MongoClientURI clientURI = new MongoClientURI(URI);
		
		MongoClient client = new MongoClient(clientURI);
		MongoDatabase mongoDatabase = client.getDatabase("Deathzone");
		colletion = mongoDatabase.getCollection("server");

		
		plugin.getLogger().log(Level.INFO, "Database Connected!");
	}
	
	public void createPorfile(Player player) {
		
		Document profile = new Document("UUDI", player.getUniqueId().toString());
		Document foundProfile = (Document) colletion.find(profile).first();
		
		if (foundProfile == null) {
			profile.append("rank", (String) "dafault");
			profile.append("balance", (int) 50);
			
			colletion.insertOne(profile);
			playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId(), "default", 50));
			plugin.getLogger().log(Level.INFO , "Profile Created for user: " + player.getName() + " with UUID: " + player.getUniqueId());
		}
		
		else {
			
			String rank = (String) profile.get("rank");
			int balance = profile.getInteger("balance");
			
			playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId(), rank, balance));
			
			plugin.getLogger().log(Level.INFO, "Welcome back " + player.getName());
		}
	}
}