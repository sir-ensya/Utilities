import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class CollectionUtil {

    private CollectionUtil() {
        throw new IllegalStateException();
    }

    public static <T> @Nullable T filterAndGet(@NotNull Collection<T> collection, @NotNull Predicate<T> predicate) {
        List<T> matching = collection.stream().filter(predicate).toList();
        if (matching.size() == 0) return null;
        return matching.get(0);
    }

}
