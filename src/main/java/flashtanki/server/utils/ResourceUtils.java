package flashtanki.server.utils;

import flashtanki.server.resource.ServerIdResource;

import java.util.Arrays;
import java.util.List;

public class ResourceUtils {
    private ResourceUtils() {}

    public static List<String> encodeId(ServerIdResource resource) {
        return Arrays.asList(
                Long.toString((resource.id & 0xff000000L) >> 24, 8),
                Long.toString((resource.id & 0x00ff0000L) >> 16, 8),
                Long.toString((resource.id & 0x0000ff00L) >> 8, 8),
                Long.toString(resource.id & 0x000000ffL, 8),
                Long.toString(resource.version, 8)
        );
    }

    public static ServerIdResource decodeId(List<String> parts) {
        return new ServerIdResource(
                (Long.parseLong(parts.get(0), 8) << 24) |
                        (Long.parseLong(parts.get(1), 8) << 16) |
                        (Long.parseLong(parts.get(2), 8) << 8) |
                        Long.parseLong(parts.get(3), 8),
                Long.parseLong(parts.get(4), 8)
        );
    }

    public static String getContentType(String fileName) {
        String extension = getFileExtension(fileName);
        switch (extension) {
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "json":
                return "application/json";
            case "xml":
                return "application/xml";
            default:
                return "application/octet-stream";
        }
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) ? fileName.substring(lastDotIndex + 1) : "";
    }
}
