do_install_append() {
    mkdir -p ${D}${systemd_unitdir}/system

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -m 0644 ${B}/src/connman.service ${D}${systemd_unitdir}/system
    fi
}
