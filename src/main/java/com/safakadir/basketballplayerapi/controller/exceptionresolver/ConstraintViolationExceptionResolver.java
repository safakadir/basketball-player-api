package com.safakadir.basketballplayerapi.controller.exceptionresolver;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import graphql.validation.ValidationError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

/*
 ConstraintViolationException will be thrown during argument validation related with @Validated annotation.
 Arguments with @NotBlank, @Min, @Max, @Size, @Pattern is not valid, then ConstraintViolationException is thrown
 */

@Component
public class ConstraintViolationExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ConstraintViolationException) {
            return GraphqlErrorBuilder.newError(env).message(ex.getMessage())
                    .errorType(ErrorType.ArgumentValidationError).build();
        }
        return null;
    }

    public enum ErrorType implements ErrorClassification {
        ArgumentValidationError;
    }
}
