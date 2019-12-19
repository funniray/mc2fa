package com.funniray.twofa.storage;

import com.funniray.twofa.Twofa;
import org.apache.commons.codec.binary.Base32;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.UUID;

public class YAMLStore implements StorageBase {

    FileConfiguration keyStore;

    public YAMLStore() {
        this.keyStore = YamlConfiguration.loadConfiguration(new File(Twofa.getInstance().getDataFolder(), "data.yml"));
    }

    private void saveData() {
        try {
            this.keyStore.save(new File(Twofa.getInstance().getDataFolder(), "data.yml"));
        } catch (IOException e) {
            Bukkit.getServer().getLogger().info("Error saving user keys.");
            e.printStackTrace();
        }
    }

    @Override
    public Key getKeyForUser(UUID uuid) {
        Base32 b32 = new Base32();
        byte[] decodedKey = b32.decode(this.keyStore.getString(uuid.toString()));
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, Twofa.getInstance().totp.getAlgorithm());
    }

    @Override
    public void setKeyForUser(UUID uuid, Key key) {
        Base32 b32 = new Base32();
        String encodedKey = b32.encodeToString(key.getEncoded());
        this.keyStore.set(uuid.toString(),encodedKey);
        this.saveData();
    }

    @Override
    public String getEncodedKey(UUID uuid) {
        return this.keyStore.getString(uuid.toString());
    }
}
