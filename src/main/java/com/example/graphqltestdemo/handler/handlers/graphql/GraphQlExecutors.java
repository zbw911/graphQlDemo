package com.example.graphqltestdemo.handler.handlers.graphql;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;

import java.util.List;

/**
 * @author zhangbaowei
 * @description:
 * @date 2021/4/23 14:11
 */
public class GraphQlExecutors {

    private final List<TypeRuntimeWiring> typeRuntimeWirings;
    private final String schema;
    private final String query;

    public GraphQlExecutors(String schema, String query, List<TypeRuntimeWiring> typeRuntimeWirings) {
        this.schema = schema;
        this.query = query;
        this.typeRuntimeWirings = typeRuntimeWirings;
    }

    public ExecutionResult getResult() {

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        final RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        for (TypeRuntimeWiring typeRuntimeWiring : typeRuntimeWirings) {
            builder.type(typeRuntimeWiring);
        }

        RuntimeWiring runtimeWiring = builder.build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = graphQL.execute(query);

        return executionResult;
    }
}
