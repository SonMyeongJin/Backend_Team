FROM openjdk:17-alpine

ARG PROFILE
ARG DB_PROD_HOST
ARG DB_PROD_USERNAME
ARG DB_PROD_PASSWD
ARG JWT_SECRET

ENV PROFILE=${PROFILE}
ENV DB_PROD_HOST=${DB_PROD_HOST}
ENV DB_PROD_USERNAME=${DB_PROD_USERNAME}
ENV DB_PROD_PASSWD=${DB_PROD_PASSWD}
ENV JWT_SECRET=${JWT_SECRET}

# Install Redis
RUN apk update && \
    apk add redis

# Copy application JAR
ARG JAR_PATH=build/libs/*.jar
COPY ${JAR_PATH} /home/server.jar

# Expose ports
EXPOSE 8080 6379

# Entry point
ENTRYPOINT sh -c "redis-server --daemonize yes && java -jar /home/server.jar"
