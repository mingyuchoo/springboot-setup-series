package com.mingyuchoo.graphql;

import com.mingyuchoo.graphql.resolver.AuthorResolver;
import com.mingyuchoo.graphql.resolver.BookResolver;
import com.mingyuchoo.graphql.resolver.BookStoreResolver;
import com.mingyuchoo.graphql.resolver.CityResolver;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    AuthorResolver authorResolver;

    @Autowired
    BookResolver bookResolver;

    @Autowired
    BookStoreResolver bookStoreResolver;

    @Autowired
    CityResolver cityResolver;

    @Value("classpath:schema/**/*.graphql")
    private Resource[] schemaResources;

    // (3)
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    // (1)
    @PostConstruct
    public void init() {
        final List<File> schemas =
                Arrays.stream(schemaResources)
                        .filter(Resource::isFile)
                        .map(
                                resource -> {
                                    try {
                                        return resource.getFile();
                                    } catch (IOException ex) {
                                        throw new RuntimeException("Unable to load schema files");
                                    }
                                })
                        .collect(Collectors.toList());

        final GraphQLSchema graphQLSchema = buildSchema(schemas);

        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    // (2)
    private GraphQLSchema buildSchema(final List<File> schemas) {
        final SchemaParser schemaParser = new SchemaParser();
        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        final TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();

        for (final File schema : schemas) {
            typeDefinitionRegistry.merge(schemaParser.parse(schema));
        }

        final RuntimeWiring runtimeWiring = buildWiring();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.Time)
                .scalar(ExtendedScalars.DateTime)
                .type(
                        TypeRuntimeWiring.newTypeWiring("Query")
                                .dataFetcher("authorCount", authorResolver.authorCount())
                                .dataFetcher("authors", authorResolver.authors())
                                .dataFetcher("author", authorResolver.author())
                                .dataFetcher("bookCount", bookResolver.bookCount())
                                .dataFetcher("books", bookResolver.books())
                                .dataFetcher("book", bookResolver.book())
                                .dataFetcher(
                                        "bookStoreCount", bookStoreResolver.bookStoreCount())
                                .dataFetcher("bookStores", bookStoreResolver.bookStores())
                                .dataFetcher("bookStore", bookStoreResolver.bookStore())
                                .dataFetcher("cityCount", cityResolver.cityCount())
                                .dataFetcher("cities", cityResolver.cities())
                                .dataFetcher("city", cityResolver.city()))
                .type(
                        TypeRuntimeWiring.newTypeWiring("Mutation")
                                .dataFetcher("addAuthor", authorResolver.addAuthor())
                                .dataFetcher("changeAuthor", authorResolver.changeAuthor())
                                .dataFetcher("removeAuthor", authorResolver.removeAuthor())
                                .dataFetcher("addBook", bookResolver.addBook())
                                .dataFetcher(
                                        "addBookWithAuthorIdAndBookStoreId",
                                        bookResolver.addBookWithAuthorIdAndBookStoreId())
                                .dataFetcher("changeBook", bookResolver.changeBook())
                                .dataFetcher("removeBook", bookResolver.removeBook())
                                .dataFetcher("addBookStore", bookStoreResolver.addBookStore())
                                .dataFetcher(
                                        "changeBookStore", bookStoreResolver.changeBookStore())
                                .dataFetcher(
                                        "removeBookStore", bookStoreResolver.removeBookStore())
                                .dataFetcher("addCity", cityResolver.addCity())
                                .dataFetcher("changeCity", cityResolver.changeCity())
                                .dataFetcher("removeCity", cityResolver.removeCity()))
                .build();
    }
}
