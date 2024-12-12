# Use the official pgAdmin image
FROM dpage/pgadmin4

# Optional: Set environment variables
ENV PGADMIN_DEFAULT_EMAIL=admin@admin.com
ENV PGADMIN_DEFAULT_PASSWORD=postgres

# Optional: Copy custom configuration files if needed
# COPY pgadmin4.conf /etc/pgadmin/pgadmin4.conf

# Expose the default pgAdmin port
EXPOSE 80

# The entryp