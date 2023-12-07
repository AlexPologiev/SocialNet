# social-net-backend

==================================

### Docker.hub credentials:

| URL          |  https://hub.docker.com/repository/docker/skillbox43/social-net-backend/general   |
|--------------|-----|
| **login**    |   skillbox43  |
| **password** |   skillbox43  |

==================================

#### Docker build and docker-compose:

From project folder you need:

1. docker login -u skillbox43 -p skillbox43

2. build and push images 
* docker build -f .deploy/dockerfile-gateway -t skillbox43/social-net-backend:gateway .
* docker build -f .deploy/dockerfile-communication -t skillbox43/social-net-backend:commutication .
* docker build -f .deploy/dockerfile-database -t skillbox43/social-net-backend:database .
* docker build -f .deploy/dockerfile-environment -t skillbox43/social-net-backend:environment .
* docker build -f .deploy/dockerfile-profile -t skillbox43/social-net-backend:profile .
* docker push skillbox43/social-net-backend:gateway
* docker push skillbox43/social-net-backend:commutication
* docker push skillbox43/social-net-backend:database
* docker push skillbox43/social-net-backend:environment
* docker push skillbox43/social-net-backend:profile

3. run docker compose
* docker-compose -f .deploy/docker-compose.yaml up -d