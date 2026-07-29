package hexlet.code.util;

import java.net.URI;

public final class UrlNormalizer {
    private UrlNormalizer() {
    }

    public static String normalize(String rawUrl) {
        try {
            var uri = new URI(rawUrl.trim());
            var url = uri.toURL();
            if (url.getHost() == null || url.getHost().isBlank()) {
                throw new IllegalArgumentException("Host is empty");
            }

            var result = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                result += ":" + url.getPort();
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }
}
