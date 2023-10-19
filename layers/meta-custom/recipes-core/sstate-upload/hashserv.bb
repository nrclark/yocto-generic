LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${BITBAKEPATH}/../LICENSE;md5=7e4cfe1c8dee5c6fe34c79c38d7b6b52"

do_compile[noexec] = "1"
do_configure[noexec] = "1"
do_patch[noexec] = "1"
do_unpack[noexec] = "1"

do_install() {
    BITBAKE_DIR=$(cd "${BITBAKEPATH}/.." && pwd)
    mkdir -p "${D}${datadir}"
    cp -r "${BITBAKE_DIR}" "${D}${datadir}/bitbake"

    mkdir -p "${D}${bindir}"

    echo "#!/bin/sh" > "${D}${bindir}/bitbake-hashserv"
    echo "export PYTHON_PATH=${datadir}/bitbake/lib" >> "${D}${bindir}/bitbake-hashserv"
    echo "exec ${datadir}/bitbake/bin/bitbake-hashserv" '"$@"' >> "${D}${bindir}/bitbake-hashserv"
    chmod 0755 "${D}${bindir}/bitbake-hashserv"
}

FILES:${PN} += "${datadir}/bitbake"
RDEPENDS:${PN} += "python3"
RDEPENDS:${PN}:append:class-target = " bash coreutils"

BBCLASSEXTEND = "native nativesdk"
PACKAGES = "${PN}"
inherit allarch
