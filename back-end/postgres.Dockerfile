FROM postgres

COPY init.sql /docker-entrypoint-initdb.d/
COPY init_reservac.sql /docker-entrypoint-initdb.d/
COPY init_reservaq.sql /docker-entrypoint-initdb.d/

EXPOSE 5432/tcp