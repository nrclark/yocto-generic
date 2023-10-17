do_install:append() {
    if [ -L "${D}${STAGING_BINDIR_NATIVE}/iptables-xml" ]; then
        ln -frs "${D}${STAGING_SBINDIR_NATIVE}/xtables-legacy-multi" \
            "${D}${STAGING_BINDIR_NATIVE}/iptables-xml"
    fi
}

BBCLASSEXTEND = "native"
