package hexlet.code.util;

import java.net.URI;

public final class UrlNormalizer {
    private UrlNormalizer() {
    }

    public static String normalize(URI uri) {
        var scheme = uri.getScheme();
        var host = uri.getHost();
        if (scheme == null || host == null || host.isBlank()) {
            throw new IllegalArgumentException("Invalid URL");
        }

        var result = scheme + "://" + host;
        if (uri.getPort() != -1) {
            result += ":" + uri.getPort();
        }
        return result;
    }
}
