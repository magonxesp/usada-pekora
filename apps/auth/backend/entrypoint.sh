#!/bin/sh
# ... do some setup ...
if [ "$APP_ENV" -eq "test" ] && [ ! -d ssl ]; then
    mkdir ssl
    openssl genrsa -out ssl/private_rsa.pem 2048
    openssl pkcs8 -topk8 -inform PEM -outform PEM -in ssl/private_rsa.pem -out ssl/private.pem -nocrypt
    openssl rsa -in ssl/private.pem -outform PEM -pubout -out ssl/public.pem
fi

# then run the CMD passed as command-line arguments
exec "$@"
