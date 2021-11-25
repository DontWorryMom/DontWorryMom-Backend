package com.backend.Deserializers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.type.EnumType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

/*
 * Helper class to help deserialize/serialize between Java Enums 
 * and PostgreSQL enums
 * 
 * Hibernate throws an error when storing POJO Enum into PostgreSQL 
 * without this helper class
 * 
 * Taken from:
 * https://stackoverflow.com/questions/27804069/hibernate-mapping-between-postgresql-enum-and-java-enum
 */

public class PostgreSQLEnumType extends EnumType {
     
    public void nullSafeSet(
            PreparedStatement st, 
            Object value, 
            int index, 
            SharedSessionContractImplementor session) 
        throws HibernateException, SQLException {
        if(value == null) {
            st.setNull( index, Types.OTHER );
        }
        else {
            st.setObject( 
                index, 
                value.toString(), 
                Types.OTHER 
            );
        }
    }
}