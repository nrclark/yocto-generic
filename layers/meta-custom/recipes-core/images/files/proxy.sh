#!/bin/sh

_proxy_val="$(cat /proc/cmdline | grep -oE "\bproxy=[^ ]+" | \
              sed 's/^proxy=//g')"

_no_proxy_val="$(cat /proc/cmdline | grep -oE "\bno_proxy=[^ ]+" | \
                 sed 's/^no_proxy=//g')"

if [ -n "${_proxy_val:-}" ]; then
    export http_proxy="${_proxy_val}"
    no_proxy='localhost,127.0.0.1,127.*,::1,10.*,192.168.*'

    for x in $(seq 16 31); do
        no_proxy="${no_proxy},172.$x.*"
    done

    if [ -n "${_no_proxy_val:-}" ]; then
        no_proxy="${no_proxy},${_no_proxy_val}"
    fi

    export https_proxy="${http_proxy}" \
           ftp_proxy="${http_proxy}" \
           rsync_proxy="${http_proxy}" \
           all_proxy="${http_proxy}"

    export HTTP_PROXY="${http_proxy}" \
           HTTPS_PROXY="${http_proxy}" \
           FTP_PROXY="${http_proxy}" \
           RSYNC_PROXY="${http_proxy}" \
           ALL_PROXY="${http_proxy}"

    export no_proxy
    export NO_PROXY="${no_proxy}"
fi

unset _proxy_val
unset _no_proxy_val
