import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class LinkShortener {
    Map<String, String> urlMap;
    Map<String, String> shortUrlMap;
    static final String BASE_URL = "http://short.url/";
    static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final int SHORT_URL_LENGTH = 6;

    LinkShortener() {
        urlMap = new HashMap<>();
        shortUrlMap = new HashMap<>();
    }

    String shortenURL(String longUrl) {
        if (urlMap.containsKey(longUrl)) {
            return BASE_URL + urlMap.get(longUrl);
        }

        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (shortUrlMap.containsKey(shortUrl));

        urlMap.put(longUrl, shortUrl);
        shortUrlMap.put(shortUrl, longUrl);

        return BASE_URL + shortUrl;
    }

    String expandURL(String shortUrl) {
        String key = shortUrl.replace(BASE_URL, "");
        return shortUrlMap.getOrDefault(key, "Invalid short URL");
    }

    String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = (int) (Math.random() * CHAR_SET.length());
            shortUrl.append(CHAR_SET.charAt(index));
        }
        return shortUrl.toString();
    }

    public static void main(String[] args) {
        LinkShortener linkShortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longUrl = scanner.nextLine();
                    String shortUrl = linkShortener.shortenURL(longUrl);
                    System.out.println("Short URL: " + shortUrl);
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortUrlInput = scanner.nextLine();
                    String expandedUrl = linkShortener.expandURL(shortUrlInput);
                    System.out.println("Long URL: " + expandedUrl);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
