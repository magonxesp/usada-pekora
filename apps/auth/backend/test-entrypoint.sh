#!/bin/sh

echo "Executing tests through test entrypoint..."
eval "$@"
test_status=$?
echo "Tests tasks done!"

if [ ! -z "$REPORT_EMAIL_SENDER" ] \
    && [ ! -z "$REPORT_EMAIL_SMTP_SERVER" ] \
    && [ ! -z "$REPORT_EMAIL_SENDER_PASSWORD" ] \
    && [ ! -z "$REPORT_EMAIL_RECEIVER" ] \
    && [ ! -z "$REPORT_EMAIL_SENDER_NAME" ]
then
    echo "Sending test report to $REPORT_EMAIL_RECEIVER"

    mkdir -p /tmp/reports/contexts
    mkdir -p /tmp/reports/apps/bot

    if [ -d "/test/contexts/shared/build/reports" ]; then
        cp -r /test/contexts/shared/build/reports /tmp/reports/contexts/shared
    fi

    if [ -d "/test/contexts/auth/build/reports" ]; then
        cp -r /test/contexts/auth/build/reports /tmp/reports/contexts/auth
    fi

    if [ -d "/test/apps/auth/backend/build/reports" ]; then
        cp -r /test/apps/auth/backend/build/reports /tmp/reports/apps/auth/backend
    fi

    zip -r /tmp/report.zip /tmp/reports

    if [ "$test_status" -eq 0 ]; then
        subject="Usada pekora auth backend tests success"
    else
        subject="Usada pekora auth backend tests failed"
    fi

    smtp_config="
    # Set default values for all following accounts.
    defaults
    auth           on
    tls            on
    tls_trust_file /etc/ssl/certs/ca-certificates.crt
    syslog         on

    # Gmail
    account        smtp
    host           $REPORT_EMAIL_SMTP_SERVER
    port           587
    from           $REPORT_EMAIL_SENDER
    user           $REPORT_EMAIL_SENDER
    password       $REPORT_EMAIL_SENDER_PASSWORD

    # Set a default account
    account default : smtp
    aliases        /etc/aliases
    "

    echo "$smtp_config" > /etc/msmtprc

    ln -sf /usr/bin/msmtp /usr/bin/sendmail
    ln -sf /usr/bin/msmtp /usr/sbin/sendmail

    uploaded_report=`curl --upload-file /tmp/report.zip https://transfer.sh/report.zip`

    echo "Tests report archive: $uploaded_report" | mutt \
        -s "$subject" \
        -e "my_hdr From:$REPORT_EMAIL_SENDER_NAME <$REPORT_EMAIL_SENDER>" \
        -- "$REPORT_EMAIL_RECEIVER"

    echo "Test report sent to $REPORT_EMAIL_RECEIVER"
fi

if [ "$test_status" -eq 0 ]; then
    exit 0
else
    exit 1
fi
