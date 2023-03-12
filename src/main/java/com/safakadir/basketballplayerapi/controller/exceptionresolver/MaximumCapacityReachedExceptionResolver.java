package com.safakadir.basketballplayerapi.controller.exceptionresolver;

import com.safakadir.basketballplayerapi.exception.MaximumCapacityReachedException;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class MaximumCapacityReachedExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof MaximumCapacityReachedException) {
            return GraphqlErrorBuilder.newError(env).message(ex.getMessage())
                    .errorType(ErrorType.CapacityError).build();
        }
        return null;
    }

    public enum ErrorType implements ErrorClassification {
        CapacityError;
    }
}
