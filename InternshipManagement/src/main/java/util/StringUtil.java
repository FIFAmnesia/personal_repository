package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import exceptions.ValidationException;

public class StringUtil {

  private static final String HEXES = "0123456789ABCDEF";

  public static String hash(String hashSrc, String algorithm) throws ValidationException {
    MessageDigest md = null;

    try {
      md = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new ValidationException("Couldn't generate accessToken...");
    }
    md.update(hashSrc.getBytes(), 0, hashSrc.length());

    String hashPass = getHex(md.digest());

    return hashPass;
  }

  public static String getHex(byte[] raw) {
    if (raw == null) {
      return null;
    }

    final StringBuilder hex = new StringBuilder(2 * raw.length);
    for (final byte b : raw) {
      hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
    }

    return hex.toString();
  }
}
