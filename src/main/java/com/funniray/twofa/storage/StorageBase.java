package com.funniray.twofa.storage;

import java.security.Key;
import java.util.UUID;

public interface StorageBase {

    public Key getKeyForUser(UUID uuid);
    public void setKeyForUser(UUID uuid, Key key);
    public String getEncodedKey(UUID uuid);

}
