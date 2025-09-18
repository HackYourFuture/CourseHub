#!/bin/sh
cat <<EOF > /usr/share/nginx/html/config.js
// nginx-entrypoint.sh
window.__CONFIG__ = {
  backendUrl: "${BACKEND_URL}"
};
EOF
nginx -g 'daemon off;'
