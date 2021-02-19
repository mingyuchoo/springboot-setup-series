package com.mingyuchoo.graphql01;

import com.mingyuchoo.graphql01.datafetchers.AuthorDataFetcher;
import com.mingyuchoo.graphql01.datafetchers.BookDataFetcher;
import com.mingyuchoo.graphql01.datafetchers.BookStoreDataFetcher;
import com.mingyuchoo.graphql01.datafetchers.CityDataFetcher;
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

    @Autowired AuthorDataFetcher authorDataFetcher;

    @Autowired BookDataFetcher bookDataFetcher;

    @Autowired BookStoreDataFetcher bookStoreDataFetcher;

    @Autowired CityDataFetcher cityDataFetcher;

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
                                .dataFetcher("authorCount", authorDataFetcher.authorCount())
                                .dataFetcher("authors", authorDataFetcher.authors())
                                .dataFetcher("author", authorDataFetcher.author())
                                .dataFetcher("bookCount", bookDataFetcher.bookCount())
                                .dataFetcher("books", bookDataFetcher.books())
                                .dataFetcher("book", bookDataFetcher.book())
                                .dataFetcher(
                                        "bookStoreCount", bookStoreDataFetcher.bookStoreCount())
                                .dataFetcher("bookStores", bookStoreDataFetcher.bookStores())
                                .dataFetcher("bookStore", bookStoreDataFetcher.bookStore())
                                .dataFetcher("cityCount", cityDataFetcher.cityCount())
                                .dataFetcher("cities", cityDataFetcher.cities())
                                .dataFetcher("city", cityDataFetcher.city()))
                .type(
                        TypeRuntimeWiring.newTypeWiring("Mutation")
                                .dataFetcher("addAuthor", authorDataFetcher.addAuthor())
                                .dataFetcher("changeAuthor", authorDataFetcher.changeAuthor())
                                .dataFetcher("removeAuthor", authorDataFetcher.removeAuthor())
                                .dataFetcher("addBook", bookDataFetcher.addBook())
                                .dataFetcher(
                                        "addBookWithAuthorIdAndBookStoreId",
                                        bookDataFetcher.addBookWithAuthorIdAndBookStoreId())
                                .dataFetcher("changeBook", bookDataFetcher.changeBook())
                                .dataFetcher("removeBook", bookDataFetcher.removeBook())
                                .dataFetcher("addBookStore", bookStoreDataFetcher.addBookStore())
                                .dataFetcher(
                                        "changeBookStore", bookStoreDataFetcher.changeBookStore())
                                .dataFetcher(
                                        "removeBookStore", bookStoreDataFetcher.removeBookStore())
                                .dataFetcher("addCity", cityDataFetcher.addCity())
                                .dataFetcher("changeCity", cityDataFetcher.changeCity())
                                .dataFetcher("removeCity", cityDataFetcher.removeCity()))
                .build();
    }
}
