package com.funniray.twofa;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.funniray.twofa.storage.StorageBase;
import com.funniray.twofa.storage.YAMLStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.NoSuchAlgorithmException;

public final class Twofa extends JavaPlugin {

    private static Twofa instance;
    private StorageBase keyStore;
    public TimeBasedOneTimePasswordGenerator totp;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        try {
            this.totp = new TimeBasedOneTimePasswordGenerator();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.keyStore = new YAMLStore();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Twofa getInstance() {
        return instance;
    }

    public StorageBase getKeyStore() {
        return this.keyStore;
    }
}
