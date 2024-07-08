package flashtanki.server.protocol;

import flashtanki.server.ServerProperties;

public class ProtocolUtils {
    public static String decrypt(String data, int k) {
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
        } catch (NumberFormatException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static String crypt(String data, int k) {
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
        } catch (NumberFormatException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

	public static String[] getArgsFromPacket(String decrypt) {
		//TODO TitanoMachina get args logic
		String[] decrypted = decrypt.split(ServerProperties.DELIM_ARGUMENTS_SYMBOL);
		int b = 2;
		String[] args = new String[decrypted.length - 2];
		for (b = 2; b < decrypted.length; b++) {
			args[b - 2] = decrypted[b];
		}
		return args;
	}
	
	public static String getNameFromPacket(String decrypted) {
		return decrypted.split(ServerProperties.DELIM_ARGUMENTS_SYMBOL)[1];
	}
	
	public static String getTypeFromPacket(String decrypted) {
		return decrypted.split(";")[0];
	}
}
