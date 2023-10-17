EXTRA_OEMESON:append:class-native = "-Dudevrulesdir=${D}/${sysconfdir}/udev/rules.d"

do_install:prepend:class-native() {
    mkdir -p "${D}/dev"
    touch "${D}/dev/fuse"
}

DEPENDS:remove:class-native = "udev"
BBCLASSEXTEND = "native"
