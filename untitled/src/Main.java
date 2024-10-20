import org.w3c.dom.Document;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // Добавить начальные страницы (Seed URLs)
        queue.add("http://example.com");

        while (!queue.isEmpty()) {
            String url = queue.poll();

            if (!visited.contains(url)) {
                visited.add(url);
                try {
                    // Загружаем страницу
                    Document doc = Jsoup.connect(url).get();
                    // Извлекаем ссылки
                    Elements links = doc.select("a[href]");
                    for (Element link : links) {
                        String absUrl = link.attr("abs:href");
                        // Добавляем в очередь для дальнейшего обхода
                        if (!visited.contains(absUrl)) {
                            queue.add(absUrl);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при обработке: " + url);
                }
            }
        }

    }
}