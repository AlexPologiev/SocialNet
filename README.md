# social-net-backend

==================================

### Docker.hub credentials:

| URL          |  https://hub.docker.com/repository/docker/skillbox43/social-net-backend/general   |
|--------------|-----|
| **login**    |   skillbox43  |
| **password** |   skillbox43  |

==================================

### Docker build and docker-compose:

1. Specify parameters in `application-docker.yaml`: 

   - frontend url:
     
         frontend:
           url:

   - database connection parameters:

         spring:
           datasource:
             url:
             username:
             password:
             driver-class-name:
    
2. Specify database connection parameters in `microservices/database/pom.xml` for profile `docker`:

       <profile>
           <id>docker</id>
           <properties>
               <env>docker</env>
               <db.host></db.host>
               <db.login></db.login>
               <db.password></db.password>
               <db.database></db.database>
               <db.url>jdbc:postgresql://${db.host}:5432/${db.database}</db.url>
           </properties>
       </profile>

3. Project compilation and packaging: 

       clean install -P docker

4. Login into Docker Hub (perform command from project folder):
 
       docker login -u skillbox43 -p skillbox43

5. Build images (perform commands from project folder):

       docker build -f .deploy/dockerfile-gateway -t skillbox43/social-net-backend:gateway .
       docker build -f .deploy/dockerfile-communication -t skillbox43/social-net-backend:communication .
       docker build -f .deploy/dockerfile-database -t skillbox43/social-net-backend:database .
       docker build -f .deploy/dockerfile-friendship -t skillbox43/social-net-backend:friendship .
       docker build -f .deploy/dockerfile-profile -t skillbox43/social-net-backend:profile . 

6. Push images into Docker Hub (perform commands from project folder):

       docker push skillbox43/social-net-backend:gateway
       docker push skillbox43/social-net-backend:communication
       docker push skillbox43/social-net-backend:database
       docker push skillbox43/social-net-backend:friendship
       docker push skillbox43/social-net-backend:profile

7. Create containers using docker compose:

       docker-compose -p="team43-socialnet-backend" -f .deploy/docker-compose.yaml up -d