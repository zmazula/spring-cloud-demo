FROM spotify/cassandra
ADD cass.sql cass.sql
RUN cassandra && \
    cqlsh -f cass.sql