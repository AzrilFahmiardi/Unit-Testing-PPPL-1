import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class OwnerArgumentsProvider implements ArgumentsProvider {
    
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
            Arguments.of(new Owner("101", "Sarah Connor", "sarah@skynet.com")),
            Arguments.of(new Owner("102", "Kyle Reese", "kyle@resistance.com")),
            Arguments.of(new Owner("103", "John Connor", "john@resistance.com")),
            Arguments.of(new Owner("104", "Marcus Wright", "marcus@resistance.com")),
            Arguments.of(new Owner("105", "Kate Brewster", "kate@resistance.com"))
        );
    }
}
