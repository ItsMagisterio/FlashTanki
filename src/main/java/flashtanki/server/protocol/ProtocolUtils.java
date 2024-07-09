package flashtanki.server.protocol;

import flashtanki.server.ServerProperties;

public class ProtocolUtils {

    public static String decrypt(String data, int k) {
        if (data == null || data.length() < 2) {
            return null;
        }

        try {
            int key = Integer.parseInt(data.substring(0, 1));
            String data_string = data.split(ServerProperties.DELIM_COMMANDS_SYMBOL)[0].substring(1);
            if (data_string.length() != 0) {
                char[] w = data_string.toCharArray();
                for (int i = 0; i < w.length; i++) {
                    w[i] -= key + k;
                }
                data_string = new String(w);
            }
            return data_string.trim();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static String crypt(String data, int k) {
        if (data == null || data.length() < 2) {
            return null;
        }

        try {
            int key = Integer.parseInt(data.substring(0, 1));
            String data_string = data.substring(1);
            if (data_string.length() != 0) {
                char[] w = data_string.toCharArray();
                for (int i = 0; i < w.length; i++) {
                    w[i] += key + k;
                }
                data_string = new String(w);
            }
            return data_string;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static String[] getArgsFromPacket(String decrypt) {
        if (decrypt == null) {
            return new String[0];
        }

        String[] decrypted = decrypt.split(ServerProperties.DELIM_ARGUMENTS_SYMBOL);
        if (decrypted.length <= 2) {
            return new String[0];
        }

        String[] args = new String[decrypted.length - 2];
        System.arraycopy(decrypted, 2, args, 0, args.length);
        return args;
    }

    public static String getNameFromPacket(String decrypted) {
        if (decrypted == null) {
            return null;
        }

        String[] parts = decrypted.split(ServerProperties.DELIM_ARGUMENTS_SYMBOL);
        if (parts.length > 1) {
            return parts[1];
        }
        return null;
    }

    public static String getTypeFromPacket(String decrypted) {
        if (decrypted == null) {
            return null;
        }

        String[] parts = decrypted.split(";");
        if (parts.length > 0) {
            return parts[0];
        }
        return null;
    }
}
