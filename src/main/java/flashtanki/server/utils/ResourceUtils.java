package flashtanki.server.utils;

import flashtanki.server.resource.ServerIdResource;

import java.util.Arrays;
import java.util.List;

public class ResourceUtils {
    private ResourceUtils() {}

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
        return switch (extension) {
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "json" -> "application/json";
            case "xml" -> "application/xml";
            default -> "application/octet-stream";
        };
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) ? fileName.substring(lastDotIndex + 1) : "";
    }
}
