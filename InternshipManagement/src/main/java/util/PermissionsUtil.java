package util;

/**
 * Determine whether action permission is present based on binary equivalent of the permission number parameter:
 *
 * P - permission number
 * D - delete
 * U - update
 * C - create
 * R - read
 *
 * +----+------+
 * | P  | DUCR |
 * +----+------+
 * | 0  | 0000 |
 * | 1  | 0001 |
 * | 2  | 0010 |
 * | 3  | 0011 |
 * | 4  | 0100 |
 * | 5  | 0101 |
 * | 6  | 0110 |
 * | 7  | 0111 |
 * | 8  | 1000 |
 * | 9  | 1001 |
 * | 10 | 1010 |
 * | 11 | 1011 |
 * | 12 | 1100 |
 * | 13 | 1101 |
 * | 14 | 1110 |
 * | 15 | 1111 |
 * +----+------+
 *
 */
public class PermissionsUtil {

  public static boolean hasReadPermission(int permission) {
    return hasPermission(1, permission);
  }

  public static boolean hasCreatePermission(int permission) {
    return hasPermission(2, permission);
  }

  public static boolean hasUpdatePermission(int permission) {
    return hasPermission(3, permission);
  }

  public static boolean hasDeletePermission(int permission) {
    return hasPermission(4, permission);
  }

  private static boolean hasPermission(int power, int permission) {
    if (permission == 0) {
      return false;
    }
    int floor = (int) Math.pow(2, power);

    return ((permission % floor) >= (floor/2));
  }

}