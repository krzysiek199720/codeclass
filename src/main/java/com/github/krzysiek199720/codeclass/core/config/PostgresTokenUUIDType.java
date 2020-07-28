package com.github.krzysiek199720.codeclass.core.config;

import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

// Could not find any working solution on the internet, except for this one
// :(

public class PostgresTokenUUIDType extends PostgresUUIDType implements ResultSetIdentifierConsumer {

    // Naively look for a name that contains "token" in it, case insensitive
    private static final Pattern tokenPattern = Pattern.compile("token", Pattern.CASE_INSENSITIVE);

    public String getName() {
        return "pg-token-uuid";
    }

    @Override
    public UUID consumeIdentifier(final ResultSet resultSet) throws IdentifierGenerationException {
        try {
            final int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                int columnType = resultSet.getMetaData().getColumnType(i);
                // Postgres driver maps UUID to OTHER
                if (Types.OTHER == columnType) {
                    final String name = resultSet.getMetaData().getColumnName(i);

                    if (tokenPattern.matcher(name).matches()) {
                        return nullSafeGet(resultSet, name, new WrapperOptions() {
                            @Override
                            public boolean useStreamForLobBinding() {
                                return false;
                            }
                            @Override
                            public LobCreator getLobCreator() {
                                return null;
                            }
                            @Override
                            public SqlTypeDescriptor remapSqlTypeDescriptor(final SqlTypeDescriptor sqlTypeDescriptor) {
                                return PostgresUUIDSqlTypeDescriptor.INSTANCE;
                            }
                            @Override
                            public TimeZone getJdbcTimeZone() {
                                return TimeZone.getDefault();
                            }
                        });
                    }
                }
            }
            throw new IdentifierGenerationException("Could not find token column");
        } catch (SQLException e) {
            throw new IdentifierGenerationException("Error converting type", e);
        }
    }
}