BITBAKE_SRCDIR = "${@os.path.split(d.getVar('BITBAKEPATH'))[0]}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SRC_URI = " \
    file://sstate-prune.service \
    file://sstate-prune.timer \
    file://service-sstate-cache.py \
"

do_install() {
    mkdir -p ${D}${bindir}
    install -m 0755 ${WORKDIR}/service-sstate-cache.py ${D}${bindir}/

    mkdir -p ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/sstate-prune.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/sstate-prune.timer ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "${systemd_unitdir}"
RDEPENDS:${PN} += "python3 python3-core"
RDEPENDS:${PN}:class-target += "python3 python3-core findutils"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "sstate-prune.timer"

BBCLASSEXTEND = "native nativesdk"
PACKAGES = "${PN}"
inherit allarch
