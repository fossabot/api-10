package com.dongfg.project.api.graphql.scalar;

import com.dongfg.project.api.util.DateTimeConverter;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.util.Date;

/**
 * @author dongfg
 * @date 17-11-4
 */
public class GraphqlDate extends GraphQLScalarType {
    public GraphqlDate() {
        super("Date", "Date Type", new Coercing<Date, String>() {
            @Override
            public String serialize(Object dataFetcherResult) {
                if (dataFetcherResult instanceof Date) {
                    return DateTimeConverter.formatDate((Date) dataFetcherResult);
                }
                throw new CoercingSerializeException("Invalid value '" + dataFetcherResult + "' for Date");
            }

            @Override
            public Date parseValue(Object input) {
                if (input instanceof String) {
                    Date date = DateTimeConverter.parseDate((String) input);
                    if (date != null) {
                        return date;
                    }
                }
                throw new CoercingParseValueException("Invalid value '" + input + "' for Date");
            }

            @Override
            public Date parseLiteral(Object input) {
                if (!(input instanceof StringValue)) {
                    return null;
                }
                String value = ((StringValue) input).getValue();
                return DateTimeConverter.parseDate(value);
            }
        });
    }
}
