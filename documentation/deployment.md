# First Time Setup:

## Setting Up Basic Heroku
1. Create Heroku Account
2. Install Heroku Command Line
3. Create Pipeline

## Create App for Spring Backend
1. Create App for Pipeline for Spring backend
    - The app name will be referenced from here on as '**dwm-spring-prod**'
    - The app name can be anything, but it will be used in commands, so ensure the correct name when copying commands
2. Add Postgres Database Resource for Spring backend
    - Go to Resources tab
    - Under Add-Ons, search 'Postgres'
    - Add 'Heroku Postgres'
    - Note: The DB URL is now configured as an ENV variable in the Settings tab under Config Vars. Env key should be named 'DATABASE_URL'
3. Change Deploy stack from default (Heroku CLI likely) to Container Registry
    - heroku stack:set container --app **dwm-spring-prod**
4. Set ENV Variables in the Config Vars tab
    - {DB_URL, DB_NAME, DB_PASSWORD}
        - Extract values from DATABASE_URL (Should've been set automatically in Step 2) 
        - DATABASE_URL is of format "postgres://{username}:{password}@{host}:{port}/{dbname}"
        - see (https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java
        - DB_URL should be of format "jdbc:postgresql//{host}:{port}/{dbname}"
        - DB_NAME is username from DATABASE_URL
        - DB_PASSWORD is password from DATABASE_URL
    - TWILIO_AUTH_TOKEN
    - TWILIO_ACCOUNT_SID
    - TWILIO_PHONE_NUMBER
    - SENDGRID_EMAIL_ADDRESS
    - SENDGRID_API_KEY
    - ACCELEROMETER_CRASH_THRESHOLD
    - PORT (This variable is set automatically by Heroku at launch)
- jump to step (Pushing to Spring Backend)

# Subsequent Deployments

## Pushing to Spring Backend
1. Login to Heroku Command Line
    * heroku login
    * heroku container:login
2. Change to spring-backend directory
    * cd ./spring-backend
3. Build and Push the container to Heroku
    * heroku container:push web --app dwm-spring-prod
4. Release the container on Heroku
    * heroku container:release web --app dwm-spring-prod