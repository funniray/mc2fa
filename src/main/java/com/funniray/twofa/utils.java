package com.funniray.twofa;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.funniray.twofa.storage.StorageBase;
import org.bukkit.entity.Player;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

public class utils {

    public static boolean validateCode(int code, Player player) throws InvalidKeyException {
        Key key = Twofa.getInstance().getKeyStore().getKeyForUser(player.getUniqueId());
        TimeBasedOneTimePasswordGenerator totp = Twofa.getInstance().totp;

        Instant now = Instant.now();
        for (int i=-1; i<=1; i++) {
            Instant toTest = now.plus(totp.getTimeStep().multipliedBy(i));
            if (totp.generateOneTimePassword(key,toTest) == code)
                return true;
        }

        return false;
    }

    public static Key createKey() throws NoSuchAlgorithmException {
        TimeBasedOneTimePasswordGenerator totp = Twofa.getInstance().totp;
        KeyGenerator keyGen = KeyGenerator.getInstance(totp.getAlgorithm());
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String createKeyForPlayer(UUID uuid) throws NoSuchAlgorithmException {
        StorageBase keyStore = Twofa.getInstance().getKeyStore();
        Key key = createKey();
        keyStore.setKeyForUser(uuid,key);
        return keyStore.getEncodedKey(uuid);
    }
}
