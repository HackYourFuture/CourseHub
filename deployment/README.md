## Deployment

This directory contains Docker Compose configuration to deploy everything on a single VPS.

If the only updates are the image versions, then `watchtower` will take care of updating. Otherwise, first sync the deployment:
```bash
scp -r . user@server:/opt/course-hub/
```
then restart docker containers using SSH:
```bash
ssh user@server
cd /opt/course-hub
docker compose down
docker compose up -d
```
