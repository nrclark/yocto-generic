BITBAKE_SRCDIR = "${@os.path.split(d.getVar('BITBAKEPATH'))[0]}"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${BITBAKE_SRCDIR}/LICENSE;md5=7e4cfe1c8dee5c6fe34c79c38d7b6b52"

inherit systemd

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SRC_URI = " \
    file://${BITBAKE_SRCDIR};subdir=bitbake-src \
    file://bitbake-hashserv.service \
"

do_install() {
    BITBAKE_DIR="$(find "${WORKDIR}/bitbake-src" -type d -name bitbake | \
                   sort | head -n1)"

    rm -rf "${D}${datadir}"
    mkdir -p "${D}${datadir}"
    cp -r "$BITBAKE_DIR" "${D}${datadir}/bitbake"
    mkdir -p "${D}${bindir}"

    echo "#!/bin/sh" > "${D}${bindir}/bitbake-hashserv"
    echo "export PYTHON_PATH=${datadir}/bitbake/lib" >> "${D}${bindir}/bitbake-hashserv"
    echo "exec ${datadir}/bitbake/bin/bitbake-hashserv" '"$@"' >> "${D}${bindir}/bitbake-hashserv"
    chmod 0755 "${D}${bindir}/bitbake-hashserv"

    mkdir -p ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bitbake-hashserv.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "${datadir}/bitbake ${systemd_unitdir}"
RDEPENDS:${PN} += "python3 python3-core"
RDEPENDS:${PN}:class-target += "python3 python3-core bash coreutils perl"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "bitbake-hashserv.service"

BBCLASSEXTEND = "native nativesdk"
PACKAGES = "${PN}"
inherit allarch
